package org.lendi.umtapo.service.specific;

import org.marc4j.marc.Record;
import org.springframework.stereotype.Service;
import org.yaz4j.exception.ZoomException;

import java.util.List;

/**
 * Service to find bibliographic records.
 */
@Service
public interface RecordService {
    /**
     * Find record by ISBN number.
     *
     * @param isbn ISBN number. ISBN-10 and ISBN-13 are allowed.
     * @return Marc4j record. Null if no record.
     */
    Record findByISBN(String isbn) throws ZoomException;

    /**
     * Find records by title.
     *
     * @param title Record's title.
     * @param start Index beginning from records results.
     * @param end Index ending from records results.
     * @return Marc4j records. Empty list if no record.
     */
    List<Record> findByTitle(String title, Integer start, Integer end) throws ZoomException;

    /**
     * Find records by title.
     *
     * @param title Record's title.
     * @return Marc4j records. Empty list if no record.
     */
    List<Record> findByTitle(String title) throws ZoomException;
}
