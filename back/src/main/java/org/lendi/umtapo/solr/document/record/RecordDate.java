package org.lendi.umtapo.solr.document.record;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.solr.client.solrj.beans.Field;

/**
 * Record date entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecordDate {

    private static final String DOCUMENT_TYPE = "date";

    @Field(value = "document_type")
    private final String documentType;

    @Field
    private String publicationDate;

    @Field
    private String manufactureDate;

    /**
     * Instantiates a new Record date.
     */
    public RecordDate() {
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

    /**
     * Gets document type.
     *
     * @return the document type
     */
    public String getDocumentType() {
        return documentType;
    }
}
