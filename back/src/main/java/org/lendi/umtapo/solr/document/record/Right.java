package org.lendi.umtapo.solr.document.record;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.solr.client.solrj.beans.Field;

/**
 * Right entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Right {

    private static final String DOCUMENT_TYPE = "right";

    @Field(value = "document_type")
    private final String documentType;

    @Field
    private String recordOrigin;

    @Field
    private String transactionDate;

    @Field
    private boolean isModified;

    /**
     * Instantiates a new Right.
     */
    public Right() {
        this.documentType = DOCUMENT_TYPE;
    }

    /**
     * Gets record origin.
     *
     * @return the record origin
     */
    public String getRecordOrigin() {
        return recordOrigin;
    }

    /**
     * Sets record origin.
     *
     * @param recordOrigin the record origin
     */
    public void setRecordOrigin(String recordOrigin) {
        this.recordOrigin = recordOrigin;
    }

    /**
     * Gets transaction date.
     *
     * @return the transaction date
     */
    public String getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets transaction date.
     *
     * @param transactionDate the transaction date
     */
    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * Is modified boolean.
     *
     * @return the boolean
     */
    public boolean isModified() {
        return isModified;
    }

    /**
     * Sets modified.
     *
     * @param modified the modified
     */
    public void setModified(boolean modified) {
        isModified = modified;
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
