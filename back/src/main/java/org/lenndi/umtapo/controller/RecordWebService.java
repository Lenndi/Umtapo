package org.lenndi.umtapo.controller;

import com.github.ladutsko.isbn.ISBNException;
import org.apache.log4j.Logger;
import org.lenndi.umtapo.entity.configuration.Z3950;
import org.lenndi.umtapo.marc.transformer.impl.UnimarcToSimpleRecord;
import org.lenndi.umtapo.service.configuration.Z3950Service;
import org.lenndi.umtapo.service.specific.RecordService;
import org.lenndi.umtapo.solr.document.bean.record.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.yaz4j.exception.ZoomException;

import javax.websocket.server.PathParam;
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
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_Z3950_PROVIDER = 1;

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
        this.recordService = recordService;
        this.unimarcToSimpleRecord = unimarcToSimpleRecord;
        this.z3950Service = z3950Service;
    }

    /**
     * Gets records.
     *
     * @param title   the title
     * @param isbn    the isbn
     * @param size    the size
     * @param page    the page
     * @param z3950Id the z 3950 id
     * @return the records
     */
    @RequestMapping(
            value = "/records",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getRecords(@PathParam("title") String title,
                                     @PathParam("isbn") String isbn,
                                     @PathParam("result-size") Integer size,
                                     @PathParam("page") Integer page,
                                     @PathParam("z3950") Integer z3950Id) {

        if (size == null) {
            size = DEFAULT_RESULT_SIZE;
        }
        if (page == null) {
            page = DEFAULT_PAGE;
        }
        Pageable pageable = new PageRequest(page, size);

        Z3950 z3950;
        if (z3950Id != null) {
            z3950 = this.z3950Service.find(z3950Id);
        } else {
            z3950 = this.z3950Service.find(DEFAULT_Z3950_PROVIDER);
        }
        this.recordService.setLibrary(z3950.getId());

        if (isbn != null) {
            try {
                org.marc4j.marc.Record record = this.recordService.findByRawISBN(isbn);
                if (record != null) {
                    Record recordDocument = this.unimarcToSimpleRecord.transform(record);
                    recordDocument.getSource().setLibrary(z3950.getName());
                    recordDocument.getRight().setIsModified(false);

                    return new ResponseEntity<>(recordDocument, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } catch (final ZoomException e) {
                LOGGER.error(e.getMessage());
                return this.zoomExceptionHandling(e);
            } catch (final ISBNException e) {
                LOGGER.error(e.getMessage());
                return new ResponseEntity<>("Bad ISBN format", HttpStatus.BAD_REQUEST);
            }
        } else if (title != null) {
            try {
                List<Record> recordDocuments = new ArrayList<>();
                Page<org.marc4j.marc.Record> recordPage = this.recordService.findByTitle(title, pageable);

                if (recordPage.getContent() != null) {
                    recordPage.getContent().forEach(record -> {
                        Record recordDocument = this.unimarcToSimpleRecord.transform(record);
                        recordDocument.getSource().setLibrary(z3950.getName());
                        recordDocument.getRight().setIsModified(false);
                        recordDocuments.add(recordDocument);
                    });
                }

                Page response = new PageImpl<>(recordDocuments, pageable, recordPage.getTotalElements());

                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (final ZoomException e) {
                return this.zoomExceptionHandling(e);
            }
        } else {
            LOGGER.warn("Missing parameter: isbn or title is required");

            return new ResponseEntity<>("'title' or 'isbn' argument is required", HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity zoomExceptionHandling(ZoomException e) {
        LOGGER.fatal(e.getMessage());

        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
