package org.lenndi.umtapo.controller;

import org.apache.log4j.Logger;
import org.lenndi.umtapo.entity.configuration.Z3950;
import org.lenndi.umtapo.marc.transformer.impl.UnimarcToSimpleRecord;
import org.lenndi.umtapo.rest.ApiError;
import org.lenndi.umtapo.rest.wrapper.GenericRestWrapper;
import org.lenndi.umtapo.service.configuration.Z3950Service;
import org.lenndi.umtapo.service.specific.RecordService;
import org.lenndi.umtapo.solr.document.bean.record.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private static final int DEFAULT_RESULT_SIZE = 10;

    private final RecordService recordService;
    private final UnimarcToSimpleRecord unimarcToSimpleRecord;
    private final Z3950Service z3950Service;

    /**
     * Instantiates a new Record web service.
     *
     * @param recordService         the record service
     * @param unimarcToSimpleRecord the unimarc to simple record
     * @param z3950Service          the z 3950 service
     */
    @Autowired
    public RecordWebService(
            RecordService recordService,
            UnimarcToSimpleRecord unimarcToSimpleRecord,
            Z3950Service z3950Service
    ) {
        Assert.notNull(recordService, "Argument libraryService cannot be null.");
        Assert.notNull(unimarcToSimpleRecord, "Argument unimarcToSimpleRecord cannot be null.");
        Assert.notNull(z3950Service, "Argument z3950Service cannot be null.");
        this.recordService = recordService;
        this.unimarcToSimpleRecord = unimarcToSimpleRecord;
        this.z3950Service = z3950Service;
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
        Z3950 z3950;
        int size = DEFAULT_RESULT_SIZE;
        int page = 1;

        if (webRequest.getParameter("result-size") != null) {
            size = Integer.parseInt(webRequest.getParameter("result-size"));
        }
        if (webRequest.getParameter("page") != null) {
            page = Integer.parseInt(webRequest.getParameter("page"));
        }
        if (webRequest.getParameter("z3950") != null) {
            z3950 = this.z3950Service.find(Integer.parseInt(webRequest.getParameter("z3950")));
        } else {
            z3950 = this.z3950Service.find(1);
        }
        this.recordService.setLibrary(z3950.getId());

        List<org.marc4j.marc.Record> records = new ArrayList<>();
        List<Record> recordDocuments = new ArrayList<>();
        GenericRestWrapper<Record> recordWrapper = new GenericRestWrapper<>();
        recordWrapper.setData(recordDocuments);
        recordWrapper.setPage(page);

        if (isbn != null) {
            try {
                org.marc4j.marc.Record record = this.recordService.findByISBN(isbn);
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
                page = page - 1;
                Pageable pageable = new PageRequest(page, size);
                Page<org.marc4j.marc.Record> recordPage = this.recordService.findByTitle(title, pageable);
                if (recordPage.getContent() != null) {
                    records.addAll(recordPage.getContent());
                }
                int totalPage = recordPage.getTotalPages();
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
                Record recordDocument = this.unimarcToSimpleRecord.transform(record);
                recordDocument.getSource().setLibrary(z3950.getName());
                recordDocument.getRight().setIsModified(false);
                recordDocuments.add(recordDocument);
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
