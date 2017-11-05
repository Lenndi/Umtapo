package org.lenndi.umtapo.solr.service.implementation;

import com.github.ladutsko.isbn.ISBN;
import com.github.ladutsko.isbn.ISBNException;
import com.github.ladutsko.isbn.ISBNFormat;
import org.lenndi.umtapo.mapper.RecordMapper;
import org.lenndi.umtapo.solr.document.RecordDocument;
import org.lenndi.umtapo.solr.document.bean.record.Identifier;
import org.lenndi.umtapo.solr.document.bean.record.Record;
import org.lenndi.umtapo.solr.exception.InvalidRecordException;
import org.lenndi.umtapo.solr.repository.SolrRecordRepository;
import org.lenndi.umtapo.solr.service.SolrRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Solr record service.
 */
@Service
public class SolrRecordServiceImpl implements SolrRecordService {

    private final SolrRecordRepository recordRepository;
    private final RecordMapper recordMapper;

    /**
     * Instantiates a new Solr record service.
     *
     * @param recordRepository the record repository
     * @param recordMapper     the record mapper
     */
    @Autowired
    public SolrRecordServiceImpl(SolrRecordRepository recordRepository, RecordMapper recordMapper) {
        this.recordRepository = recordRepository;
        this.recordMapper = recordMapper;
    }

    @Override
    public Record findById(String id) {
        RecordDocument recordDocument = this.recordRepository.findById(id);

        return this.recordMapper.mapRecordDocumentToRecord(recordDocument);
    }

    @Override
    public Record findByItemId(Integer itemId) {
        RecordDocument recordDocument = this.recordRepository.findByItems(itemId.toString());

        return this.recordMapper.mapRecordDocumentToRecord(recordDocument);
    }

    @Override
    public Record save(Record record) throws InvalidRecordException {
        Identifier identifier = record.getIdentifier();
        if (record.getId() == null) {
            if (identifier != null && identifier.getSerialNumber() != null && identifier.getSerialType() != null) {
                List<Record> records;
                records = this.searchBySerialNumber(identifier.getSerialNumber(), identifier.getSerialType());

                if (records.size() > 0) {
                    return records.get(0);
                } else {
                    record.setId(UUID.randomUUID().toString());
                }
            } else {
                throw new InvalidRecordException("Record must contain serial and serialType");
            }
        }

        RecordDocument recordDocument = this.recordMapper.mapRecordToRecordDocument(record);
        recordDocument = this.recordRepository.save(recordDocument);

        return this.recordMapper.mapRecordDocumentToRecord(recordDocument);
    }

    @Override
    public void delete(String id) {
        this.recordRepository.delete(id);
    }

    @Override
    public List<Record> searchBySerialNumber(String serialNumber, String serialType) {
        List<RecordDocument> recordDocuments;
        List<Record> records = new ArrayList<>();
        recordDocuments = this.recordRepository.findBySerialNumberContainingAndSerialType(serialNumber, serialType);
        recordDocuments.forEach(recordDocument ->
            records.add(this.recordMapper.mapRecordDocumentToRecord(recordDocument))
        );

        return records;
    }

    @Override
    public Page<Record> searchByTitle(String title, Pageable pageable) {
        Page<RecordDocument> recordDocumentsPage = this.recordRepository.findByMainTitleContaining(title, pageable);

        return this.mapRecordDocumentPageToRecordPage(recordDocumentsPage);
    }

    @Override
    public List<Record> searchByTitle(String title) {
        List<RecordDocument> recordDocuments = this.recordRepository.findByMainTitleContaining(title);

        return this.mapRecordDocumentListToRecordList(recordDocuments);
    }

    @Override
    public Page<Record> searchBySerialNumberAndSerialType(String serialNumber, String serialType, Pageable page)
            throws ISBNException {
        Page<RecordDocument> recordDocuments;

        if (serialType.toUpperCase().equals("ISBN")) {
            if (ISBN.isValid(serialNumber)) {
                ISBNFormat isbnFormat = new ISBNFormat(ISBNFormat.HYPHEN_GROUP_SEPARATOR);

                if (ISBN.isIsbn13(serialNumber)) {
                    ISBN isbn = ISBN.parseIsbn(serialNumber);
                    recordDocuments = this.recordRepository.findBySerialNumberContainingAndSerialType(
                            isbnFormat.format(isbn.getIsbn13()),
                            serialType,
                            page);

                    if (recordDocuments.getTotalElements() == 0) {
                        recordDocuments = this.recordRepository.findBySerialNumberContainingAndSerialType(
                                isbnFormat.format(isbn.getIsbn10()),
                                serialType,
                                page);
                    }
                } else {
                    recordDocuments = this.recordRepository.findBySerialNumberContainingAndSerialType(
                            isbnFormat.format(ISBN.normalize(serialNumber)),
                            serialType,
                            page);
                }
            } else {
                throw new ISBNException(serialNumber + " is not a valid EAN for ISBN number.");
            }
        } else {
            recordDocuments = this.recordRepository.findBySerialNumberContainingAndSerialType(
                    serialNumber,
                    serialType,
                    page);
        }

        return this.mapRecordDocumentPageToRecordPage(recordDocuments);
    }

    @Override
    public Page<Record> fullSearch(
            String title,
            String author,
            String publisher,
            String id,
            String publicationDate,
            Pageable page
    ) {
        Page<RecordDocument> recordDocuments =
                this.recordRepository.fullSearch(title, author, publisher, id, publicationDate, page);

        return this.mapRecordDocumentPageToRecordPage(recordDocuments);
    }

    private Page<Record> mapRecordDocumentPageToRecordPage(Page<RecordDocument> recordDocumentPage) {
        List<RecordDocument> recordDocuments = recordDocumentPage.getContent();
        List<Record> records = new ArrayList<>();
        recordDocuments.forEach(recordDocument ->
                records.add(this.recordMapper.mapRecordDocumentToRecord(recordDocument))
        );
        Pageable pageable = new PageRequest(recordDocumentPage.getNumber(), recordDocumentPage.getSize());

        return new PageImpl<>(records, pageable, recordDocumentPage.getTotalElements());
    }

    private List<Record> mapRecordDocumentListToRecordList(List<RecordDocument> recordDocuments) {
        List<Record> records = new ArrayList<>();
        recordDocuments.forEach(recordDocument ->
                records.add(this.recordMapper.mapRecordDocumentToRecord(recordDocument))
        );

        return records;
    }
}
