package org.lendi.umtapo.marc.transformer.impl;

import org.lendi.umtapo.marc.transformer.RecordTransformer;
import org.lendi.umtapo.solr.document.bean.record.Contributor;
import org.lendi.umtapo.solr.document.bean.record.Coverage;
import org.lendi.umtapo.solr.document.bean.record.Creator;
import org.lendi.umtapo.solr.document.bean.record.Description;
import org.lendi.umtapo.solr.document.bean.record.Identifier;
import org.lendi.umtapo.solr.document.bean.record.Language;
import org.lendi.umtapo.solr.document.bean.record.Publisher;
import org.lendi.umtapo.solr.document.bean.record.Record;
import org.lendi.umtapo.solr.document.bean.record.RecordDate;
import org.lendi.umtapo.solr.document.bean.record.Right;
import org.lendi.umtapo.solr.document.bean.record.Source;
import org.lendi.umtapo.solr.document.bean.record.Subject;
import org.lendi.umtapo.solr.document.bean.record.Title;
import org.lendi.umtapo.solr.document.bean.record.Type;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Transform Record with UNIMARC format to a Record.
 */
@Service
public class UnimarcToSimpleRecord implements RecordTransformer<Record> {
    private org.marc4j.marc.Record record;

    @Override
    public Record transform(org.marc4j.marc.Record baseRecord) {
        this.record = baseRecord;
        Record record;

        if (this.record == null) {
            record = null;
        } else {
            record = new Record();

            record.setTitle(this.processTitle());
            record.setCreator(this.processCreator());
            record.setSubject(this.processSubject());
            record.setDescription(this.processDescription());
            record.setPublisher(this.processPublisher());
            record.setContributors(this.processContributors());
            record.setDate(this.processDate());
            record.setType(this.processType());
            record.setIdentifier(this.processIdentifier());
            record.setSource(this.processSource());
            record.setLanguage(this.processLanguage());
            record.setCoverage(this.processCoverage());
            record.setRight(this.processRight());
        }

        return record;
    }

    /**
     * Get title information from record to create a Title.
     *
     * @return Simple record title.
     */
    private Title processTitle() {
        Title title = new Title();
        DataField dataField;

        dataField = (DataField) record.getVariableField("200");
        if (dataField != null) {
            title.setMainTitle(dataField.getSubfieldsAsString("a"));
            title.setSubTitle(dataField.getSubfieldsAsString("e"));
        }

        List<VariableField> variableFields = record.getVariableFields("517");
        if (variableFields != null) {
            variableFields.forEach(variableField -> {
                DataField dataField1 = (DataField) variableField;
                title.addAlternateTitle(dataField1.getSubfieldsAsString("a"));
            });
        }

        return title;
    }

    /**
     * Get creator information from record to create a Creator.
     *
     * @return Simple record creator
     */
    private Creator processCreator() {
        Creator creator = new Creator();
        DataField dataField;

        dataField = (DataField) record.getVariableField("700");
        if (dataField != null) {
            creator.setName(dataField.getSubfieldsAsString("a"));
            creator.setSecondName(dataField.getSubfieldsAsString("b"));
            creator.setDate(dataField.getSubfieldsAsString("f"));
            creator.setTitles(dataField.getSubfieldsAsString("c"));
        } else {
            dataField = (DataField) record.getVariableField("710");

            if (dataField != null) {
                creator.setName(dataField.getSubfieldsAsString("a"));
                creator.setSecondName(dataField.getSubfieldsAsString("b"));
                creator.setDate(dataField.getSubfieldsAsString("f"));
                creator.setTitles(dataField.getSubfieldsAsString("c"));
            } else {
                dataField = (DataField) record.getVariableField("200");

                if (dataField != null) {
                    creator.setName(dataField.getSubfieldsAsString("f"));
                }
            }
        }

        return creator;
    }

    /**
     * Get subject information from record to create a Subject.
     *
     * @return Simple record subject
     */
    private Subject processSubject() {
        Subject subject = new Subject();
        DataField dataField;
        List<VariableField> variableFields;

        variableFields = record.getVariableFields("610");
        if (variableFields != null) {
            variableFields.forEach(variableField -> {
                DataField dataField1 = (DataField) variableField;
                subject.addTerm(dataField1.getSubfieldsAsString("a"));
            });
        }

        variableFields = record.getVariableFields("606");
        if (variableFields != null) {
            variableFields.forEach(variableField -> {
                DataField dataField1 = (DataField) variableField;
                subject.addTerm(dataField1.getSubfieldsAsString("a"));
            });
        }

        dataField = (DataField) record.getVariableField("675");
        if (dataField != null) {
            subject.setUniversal(dataField.getSubfieldsAsString("a"));
        }

        dataField = (DataField) record.getVariableField("676");
        if (dataField != null) {
            subject.setDewey(dataField.getSubfieldsAsString("a"));
        }

        return subject;
    }

    /**
     * Get description information from record to create a Description.
     *
     * @return Simple record description
     */
    private Description processDescription() {
        Description description = new Description();
        DataField dataField;

        dataField = (DataField) record.getVariableField("330");
        if (dataField != null) {
            description.setMainDescription(dataField.getSubfieldsAsString("a"));
        }

        dataField = (DataField) record.getVariableField("333");
        if (dataField != null) {
            description.addOtherDescription(dataField.getSubfieldsAsString("a"));
        }

        dataField = (DataField) record.getVariableField("215");
        if (dataField != null) {
            description.setMainPhysicalDescription(dataField.getSubfieldsAsString("a"));
            description.setSecondaryPhysicalDescription(dataField.getSubfieldsAsString("c"));
            description.setFormat(dataField.getSubfieldsAsString("d"));
            description.setAssociatedMaterial(dataField.getSubfieldsAsString("e"));
        }

        return description;
    }

    /**
     * Get publisher information from record to create a Publisher.
     *
     * @return Simple record publisher
     */
    private Publisher processPublisher() {
        Publisher publisher = new Publisher();

        DataField dataField = (DataField) record.getVariableField("210");
        if (dataField != null) {
            publisher.setPublicationPlace(dataField.getSubfieldsAsString("a"));
            publisher.setEditorName(dataField.getSubfieldsAsString("c"));
            publisher.setEditorAddress(dataField.getSubfieldsAsString("b"));
            publisher.setManufacturerName(dataField.getSubfieldsAsString("g"));
            publisher.setManufacturerAddress(dataField.getSubfieldsAsString("f"));
            publisher.setManufacturerPlace(dataField.getSubfieldsAsString("e"));
        }

        return publisher;
    }

    /**
     * Get contributors information from record to create List<Creator>.
     *
     * @return Simple record contributors
     */
    private List<Contributor> processContributors() {
        List<Contributor> contributors = new ArrayList<>();
        DataField dataField;
        List<VariableField> variableFields;

        String[] tags = {"701", "711"};
        variableFields = record.getVariableFields(tags);
        if (variableFields != null) {
            variableFields.forEach(variableField -> {
                Contributor contributor = new Contributor();
                DataField dataField1 = (DataField) variableField;

                contributor.setName(dataField1.getSubfieldsAsString("a"));
                contributor.setSecondName(dataField1.getSubfieldsAsString("b"));
                contributor.setDate(dataField1.getSubfieldsAsString("f"));
                contributor.setTitles(dataField1.getSubfieldsAsString("c"));

                contributors.add(contributor);
            });
        }

        dataField = (DataField) record.getVariableField("200");
        if (dataField != null) {
            Contributor contributor = new Contributor();
            contributor.setName(dataField.getSubfieldsAsString("g"));
            contributors.add(contributor);
        }

        return contributors;
    }

    /**
     * Get dates from record to create RecordDate.
     *
     * @return Simple record date
     */
    private RecordDate processDate() {
        RecordDate date = new RecordDate();
        DataField dataField;

        dataField = (DataField) record.getVariableField("210");
        if (dataField != null) {
            date.setPublicationDate(dataField.getSubfieldsAsString("d"));
            date.setManufactureDate(dataField.getSubfieldsAsString("h"));
        }

        return date;
    }

    /**
     * Get types from record to create Type.
     *
     * @return Simple record type
     */
    private Type processType() {
        Type type = new Type();

        List<VariableField> variableFields = record.getVariableFields("608");
        if (variableFields != null) {
            variableFields.forEach(variableField -> {
                DataField dataField1 = (DataField) variableField;
                type.addType(dataField1.getSubfieldsAsString("a"));
            });
        }

        return type;
    }

    /**
     * Get identifiers from record to create Identifier.
     *
     * @return Simple record identifier
     */
    private Identifier processIdentifier() {
        Identifier identifier = new Identifier();
        DataField dataField;

        ControlField  controlField = (ControlField) record.getVariableField("001");
        if (controlField != null) {
            identifier.setRecordIdentifier(controlField.getData());
        }

        dataField = (DataField) record.getVariableField("010");
        if (dataField != null) {
            identifier.setSerialNumber(dataField.getSubfieldsAsString("a"));
            identifier.setSerialType(Identifier.ISBN);
        } else if (record.getVariableField("011") != null) {
            dataField = (DataField) record.getVariableField("011");
            identifier.setSerialNumber(dataField.getSubfieldsAsString("a"));
            identifier.setSerialType(Identifier.ISSN);
        } else if (record.getVariableField("013") != null) {
            dataField = (DataField) record.getVariableField("013");
            identifier.setSerialNumber(dataField.getSubfieldsAsString("a"));
            identifier.setSerialType(Identifier.ISMN);
        } else if (record.getVariableField("015") != null) {
            dataField = (DataField) record.getVariableField("015");
            identifier.setSerialNumber(dataField.getSubfieldsAsString("a"));
            identifier.setSerialType(Identifier.ISRN);
        } else if (record.getVariableField("016") != null) {
            dataField = (DataField) record.getVariableField("016");
            identifier.setSerialNumber(dataField.getSubfieldsAsString("a"));
            identifier.setSerialType(Identifier.ISRC);
        }

        dataField = (DataField) record.getVariableField("073");
        if (dataField != null) {
            identifier.setBarCode(dataField.getSubfieldsAsString("a"));
        }

        return identifier;
    }

    /**
     * Get source from record to create a Source.
     *
     * @return Simple record source
     */
    private Source processSource() {
        Source source = new Source();

        ControlField  controlField = (ControlField) record.getVariableField("003");
        if (controlField != null) {
            source.setUrl(controlField.getData());
        }

        return source;
    }

    /**
     * Get language from record to create a Language.
     *
     * @return Simple record language
     */
    private Language processLanguage() {
        Language language = new Language();
        List<Subfield> subfields;

        DataField dataField = (DataField) record.getVariableField("101");
        if (dataField != null) {
            language.setMainLanguage(dataField.getSubfieldsAsString("a"));
            language.setOriginalLanguage(dataField.getSubfieldsAsString("c"));
            subfields = dataField.getSubfields("j");
            subfields.forEach(subfield -> language.addSubtitle(subfield.getData()));
            subfields = dataField.getSubfields("i");
            subfields.forEach(subfield -> language.addOther(subfield.getData()));
        }

        return language;
    }

    /**
     * Get coverage from record to create Coverage.
     *
     * @return Simple record coverage
     */
    private Coverage processCoverage() {
        Coverage coverage = new Coverage();

        DataField dataField = (DataField) record.getVariableField("300");
        if (dataField != null) {
            coverage.setGeneralNote(dataField.getSubfieldsAsString("a"));
        }

        return coverage;
    }

    /**
     * Get rights from record to create Right.
     * @return Simple record right
     */
    private Right processRight() {
        Right right = new Right();
        DataField dataField;

        dataField = (DataField) record.getVariableField("801");
        if (dataField != null) {
            right.setRecordOrigin(dataField.getSubfieldsAsString("b"));
            right.setTransactionDate(dataField.getSubfieldsAsString("c"));
        }

        return right;
    }
}
