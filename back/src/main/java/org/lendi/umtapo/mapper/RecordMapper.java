package org.lendi.umtapo.mapper;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.lendi.umtapo.solr.document.ContributorDocument;
import org.lendi.umtapo.solr.document.RecordDocument;
import org.lendi.umtapo.solr.document.bean.record.Contributor;
import org.lendi.umtapo.entity.Record;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

/**
 * The type Record mapper.
 */
@Component
public class RecordMapper {

    private static final MapperFacade DOCUMENT_MAPPER;
    private static final MapperFacade CONTRIBUTOR_DOCUMENT_MAPPER;

    static {
        final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        mapperFactory.getConverterFactory().registerConverter(new PassThroughConverter(ZonedDateTime.class));

        mapperFactory.classMap(Record.class, RecordDocument.class)
                .field("title.mainTitle", "mainTitle")
                .field("title.subTitle", "subTitle")
                .field("title.alternateTitles", "alternateTitles")
                .field("title.uniformTitle", "uniformTitle")
                .field("creator.name", "name")
                .field("creator.secondName", "secondName")
                .field("creator.date", "date")
                .field("creator.titles", "titles")
                .field("subject.terms", "terms")
                .field("subject.dewey", "dewey")
                .field("subject.universal", "universal")
                .field("description.mainDescription", "mainDescription")
                .field("description.otherDescriptions", "otherDescriptions")
                .field("description.mainPhysicalDescription", "mainPhysicalDescription")
                .field("description.secondaryPhysicalDescription", "secondaryPhysicalDescription")
                .field("description.format", "format")
                .field("description.associatedMaterial", "associatedMaterial")
                .field("publisher.publicationPlace", "publicationPlace")
                .field("publisher.editorName", "editorName")
                .field("publisher.editorAddress", "editorAddress")
                .field("publisher.manufacturerName", "manufacturerName")
                .field("publisher.manufacturerPlace", "manufacturerPlace")
                .field("publisher.manufacturerAddress", "manufacturerAddress")
                .field("date.publicationDate", "publicationDate")
                .field("date.manufactureDate", "manufactureDate")
                .field("type.types", "types")
                .field("identifier.recordIdentifier", "recordIdentifier")
                .field("identifier.serialNumber", "serialNumber")
                .field("identifier.serialType", "serialType")
                .field("identifier.barCode", "barCode")
                .field("source.library", "library")
                .field("source.url", "url")
                .field("language.mainLanguage", "mainLanguage")
                .field("language.originalLanguage", "originalLanguage")
                .field("language.subtitles", "subtitles")
                .field("language.others", "others")
                .field("coverage.generalNote", "generalNote")
                .field("right.recordOrigin", "recordOrigin")
                .field("right.transactionDate", "transactionDate")
                .field("right.isModified", "isModified")
                .byDefault()
                .register();
        DOCUMENT_MAPPER = mapperFactory.getMapperFacade();

        mapperFactory.classMap(Contributor.class, ContributorDocument.class)
            .byDefault()
            .register();
        CONTRIBUTOR_DOCUMENT_MAPPER = mapperFactory.getMapperFacade();
    }

    /**
     * Map record to record document record document.
     *
     * @param record the record
     * @return the record document
     */
    public RecordDocument mapRecordToRecordDocument(Record record) {
        return DOCUMENT_MAPPER.map(record, RecordDocument.class);
    }

    /**
     * Map record documentto record record.
     *
     * @param recordDocument the record document
     * @return the record
     */
    public Record mapRecordDocumentToRecord(RecordDocument recordDocument) {
        return DOCUMENT_MAPPER.map(recordDocument, Record.class);
    }

    /**
     * Map contributor to contributor document contributor document.
     *
     * @param contributor the contributor
     * @return the contributor document
     */
    public ContributorDocument mapContributorToContributorDocument(Contributor contributor) {
        return CONTRIBUTOR_DOCUMENT_MAPPER.map(contributor, ContributorDocument.class);
    }

    /**
     * Map contributor document to contributor contributor.
     *
     * @param contributorDocument the contributor document
     * @return the contributor
     */
    public Contributor mapContributorDocumentToContributor(ContributorDocument contributorDocument) {
        return CONTRIBUTOR_DOCUMENT_MAPPER.map(contributorDocument, Contributor.class);
    }
}
