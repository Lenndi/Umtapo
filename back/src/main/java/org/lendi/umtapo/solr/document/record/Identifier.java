package org.lendi.umtapo.solr.document.record;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.solr.client.solrj.beans.Field;

/**
 * Identifier entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Identifier {

    private static final String DOCUMENT_TYPE = "identifier";

    /**
     * The constant ISBN.
     */
    public static final String ISBN = "ISBN";
    /**
     * The constant ISSN.
     */
    public static final String ISSN = "ISSN";
    /**
     * The constant ISMN.
     */
    public static final String ISMN = "ISMN";
    /**
     * The constant ISRN.
     */
    public static final String ISRN = "ISRN";
    /**
     * The constant ISRC.
     */
    public static final String ISRC = "ISRC";

    @Field(value = "document_type")
    private final String documentType;

    @Field
    private String recordIdentifier;

    @Field
    private String serialNumber;

    @Field
    private String serialType;

    @Field
    private String barCode;

    /**
     * Instantiates a new Identifier.
     */
    public Identifier() {
        this.documentType = DOCUMENT_TYPE;
    }

    /**
     * Gets serial number.
     *
     * @return the serial number
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets serial number.
     *
     * @param serialNumber the serial number
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * Gets record identifier.
     *
     * @return the record identifier
     */
    public String getRecordIdentifier() {
        return recordIdentifier;
    }

    /**
     * Sets record identifier.
     *
     * @param recordIdentifier the record identifier
     */
    public void setRecordIdentifier(String recordIdentifier) {
        this.recordIdentifier = recordIdentifier;
    }

    /**
     * Gets serial type.
     *
     * @return the serial type
     */
    public String getSerialType() {
        return serialType;
    }

    /**
     * Sets serial type.
     *
     * @param serialType the serial type
     */
    public void setSerialType(String serialType) {
        this.serialType = serialType;
    }

    /**
     * Gets bar code.
     *
     * @return the bar code
     */
    public String getBarCode() {
        return barCode;
    }

    /**
     * Sets bar code.
     *
     * @param barCode the bar code
     */
    public void setBarCode(String barCode) {
        this.barCode = barCode;
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
