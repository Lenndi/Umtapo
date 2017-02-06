package org.lendi.umtapo.solr.document.record.simple;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.solr.client.solrj.beans.Field;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Record date entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleRecordDate {

    private static final String DOCUMENT_TYPE = "date";

    @Field(value = "document_type")
    private final String documentType;

    @Field
    private String publicationDate;

    @Field
    private String manufactureDate;

    public SimpleRecordDate() {
        this.documentType = DOCUMENT_TYPE;
    }

    /**
     * Gets publication date.
     *
     * @return the publication date
     */
    public String getPublicationDate() {
        return publicationDate;
    }

    /**
     * Sets publication date.
     *
     * @param publicationDate the publication date
     */
    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    /**
     * Gets manufacture date.
     *
     * @return the manufacture date
     */
    public String getManufactureDate() {
        return manufactureDate;
    }

    /**
     * Sets manufacture date.
     *
     * @param manufactureDate the manufacture date
     */
    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public String getDocumentType() {
        return documentType;
    }
}
