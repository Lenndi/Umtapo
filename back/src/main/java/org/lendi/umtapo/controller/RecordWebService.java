package org.lendi.umtapo.controller;

import org.apache.log4j.Logger;
import org.lendi.umtapo.entity.record.RecordListWrapper;
import org.lendi.umtapo.entity.record.simple.SimpleRecord;
import org.lendi.umtapo.rest.ApiError;
import org.lendi.umtapo.rest.wrapper.GenericRestWrapper;
import org.lendi.umtapo.marc.transformer.impl.UnimarcToSimpleRecord;
import org.lendi.umtapo.service.specific.RecordService;
import org.marc4j.marc.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.yaz4j.exception.ZoomException;

import java.util.ArrayList;
import java.util.List;

/**
 * Record web service.
 */
@RestController
@CrossOrigin
@ControllerAdvice
public class RecordWebService extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = Logger.getLogger(RecordWebService.class);

    private final RecordService recordService;
    private final UnimarcToSimpleRecord unimarcToSimpleRecord;

    /**
     * Instantiates a new Record web service.
     *
     * @param recordService         the record service
     * @param unimarcToSimpleRecord the unimarc to simple record
     */
    @Autowired
    public RecordWebService(RecordService recordService, UnimarcToSimpleRecord unimarcToSimpleRecord) {
        Assert.notNull(recordService, "Argument libraryService cannot be null.");
        Assert.notNull(unimarcToSimpleRecord, "Argument unimarcToSimpleRecord cannot be null.");
        this.recordService = recordService;
        this.unimarcToSimpleRecord = unimarcToSimpleRecord;
    }

    /**
     * Gets records.
     *
     * @param webRequest the web request
     * @return the records
     */
    @RequestMapping(
            value = "/records",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getRecords(WebRequest webRequest) {
        String title = webRequest.getParameter("title");
        String isbn = webRequest.getParameter("isbn");
        int resultSize = 10;
        int page = 1;

        if (webRequest.getParameter("result-size") != null) {
            resultSize = Integer.parseInt(webRequest.getParameter("result-size"));
        }
        if (webRequest.getParameter("page") != null) {
            page = Integer.parseInt(webRequest.getParameter("page"));
        }
        if (webRequest.getParameter("z3950") != null) {
            this.recordService.setDefaultLibrary(Integer.parseInt(webRequest.getParameter("z3950")));
        }

        List<Record> records = new ArrayList<>();
        List<SimpleRecord> simpleRecords = new ArrayList<>();
        GenericRestWrapper<SimpleRecord> recordWrapper = new GenericRestWrapper<>();
        recordWrapper.setData(simpleRecords);
        recordWrapper.setPage(page);

        if (isbn != null) {
            try {
                Record record = this.recordService.findByISBN(isbn);
                if (record != null) {
                    records.add(record);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } catch (final ZoomException e) {
                return this.zoomExceptionHandling(e);
            }
        } else if (title != null) {
            try {
                int start = resultSize * (page - 1);
                RecordListWrapper<Record> recordListWrapper = this.recordService.findByTitle(title, start, resultSize);
                if (recordListWrapper.getRecord() != null) {
                    records.addAll(recordListWrapper.getRecord());
                }
                int totalPage = (int) Math.ceil(recordListWrapper.getHitCount() / resultSize);
                recordWrapper.setTotalPage(totalPage);
            } catch (final ZoomException e) {
                return this.zoomExceptionHandling(e);
            }
        } else {
            LOGGER.warn("Missing parameter: isbn or title is required");
            ApiError apiError = new ApiError(
                    HttpStatus.BAD_REQUEST,
                    "'title' or 'isbn' argument is required",
                    "Missing parameters");

            return new ResponseEntity<>(apiError, apiError.getStatus());
        }

        if (records.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            records.forEach(record -> {
                SimpleRecord simpleRecord = this.unimarcToSimpleRecord.transform(record);
                simpleRecord.getRight().setModified(false);
                simpleRecords.add(simpleRecord);
            });
        }

        return new ResponseEntity<>(recordWrapper, HttpStatus.OK);
    }

    private ResponseEntity zoomExceptionHandling(ZoomException e) {
        LOGGER.fatal(e.getMessage());
        ApiError apiError = new ApiError(
                HttpStatus.SERVICE_UNAVAILABLE,
                e.getLocalizedMessage(),
                "Z39.50 error");

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
