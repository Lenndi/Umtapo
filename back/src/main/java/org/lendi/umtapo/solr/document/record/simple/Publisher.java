package org.lendi.umtapo.solr.document.record.simple;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.solr.client.solrj.beans.Field;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Publisher entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Publisher {

    private static final String DOCUMENT_TYPE = "publisher";

    @Field(value = "document_type")
    private final String documentType;

    @Field
    private String publicationPlace;

    @Field
    private String editorName;

    @Field
    private String editorAddress;

    @Field
    private String manufacturerName;

    @Field
    private String manufacturerPlace;

    @Field
    private String manufacturerAddress;

    public Publisher() {
        this.documentType = DOCUMENT_TYPE;
    }

    /**
     * Gets publication place.
     *
     * @return the publication place
     */
    public String getPublicationPlace() {
        return publicationPlace;
    }

    /**
     * Sets publication place.
     *
     * @param publicationPlace the publication place
     */
    public void setPublicationPlace(String publicationPlace) {
        this.publicationPlace = publicationPlace;
    }

    /**
     * Gets editor name.
     *
     * @return the editor name
     */
    public String getEditorName() {
        return editorName;
    }

    /**
     * Sets editor name.
     *
     * @param editorName the editor name
     */
    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    /**
     * Gets editor address.
     *
     * @return the editor address
     */
    public String getEditorAddress() {
        return editorAddress;
    }

    /**
     * Sets editor address.
     *
     * @param editorAddress the editor address
     */
    public void setEditorAddress(String editorAddress) {
        this.editorAddress = editorAddress;
    }

    /**
     * Gets manufacturer place.
     *
     * @return the manufacturer place
     */
    public String getManufacturerPlace() {
        return manufacturerPlace;
    }

    /**
     * Sets manufacturer place.
     *
     * @param manufacturerPlace the manufacturer place
     */
    public void setManufacturerPlace(String manufacturerPlace) {
        this.manufacturerPlace = manufacturerPlace;
    }

    /**
     * Gets manufacturer address.
     *
     * @return the manufacturer address
     */
    public String getManufacturerAddress() {
        return manufacturerAddress;
    }

    /**
     * Sets manufacturer address.
     *
     * @param manufacturerAddress the manufacturer address
     */
    public void setManufacturerAddress(String manufacturerAddress) {
        this.manufacturerAddress = manufacturerAddress;
    }

    /**
     * Gets manufacturer name.
     *
     * @return the manufacturer name
     */
    public String getManufacturerName() {
        return manufacturerName;
    }

    /**
     * Sets manufacturer name.
     *
     * @param manufacturerName the manufacturer name
     */
    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getDocumentType() {
        return documentType;
    }
}
