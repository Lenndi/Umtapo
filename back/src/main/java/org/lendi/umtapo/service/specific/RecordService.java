package org.lendi.umtapo.service.specific;

import org.lendi.umtapo.entity.record.RecordListWrapper;
import org.marc4j.marc.Record;
import org.springframework.stereotype.Service;
import org.yaz4j.exception.ZoomException;

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
     * @throws ZoomException the zoom exception
     */
    Record findByISBN(String isbn) throws ZoomException;

    /**
     * Find records by title.
     *
     * @param title Record's title.
     * @param start Index beginning from records results.
     * @param end   Index ending from records results.
     * @return Marc4j records. Empty list if no record.
     * @throws ZoomException the zoom exception
     */
    RecordListWrapper<Record> findByTitle(String title, Integer start, Integer end) throws ZoomException;

    /**
     * Find records by title.
     *
     * @param title Record's title.
     * @return Marc4j records. Empty list if no record.
     * @throws ZoomException the zoom exception
     */
    RecordListWrapper<Record> findByTitle(String title) throws ZoomException;

    /**
     * Define the default library based on Z39.50 configuration number.
     *
     * @param libraryId Z39.50 id in z39-50.yml file
     */
    void setDefaultLibrary(int libraryId);
}
