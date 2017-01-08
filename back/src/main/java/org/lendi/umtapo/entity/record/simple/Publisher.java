package org.lendi.umtapo.entity.record.simple;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Publisher entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Publisher {
    private String publicationPlace;
    private String editorName;
    private String editorAddress;
    private String manufacturerName;
    private String manufacturerPlace;
    private String manufacturerAddress;

    public String getPublicationPlace() {
        return publicationPlace;
    }

    public void setPublicationPlace(String publicationPlace) {
        this.publicationPlace = publicationPlace;
    }

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    public String getEditorAddress() {
        return editorAddress;
    }

    public void setEditorAddress(String editorAddress) {
        this.editorAddress = editorAddress;
    }

    public String getManufacturerPlace() {
        return manufacturerPlace;
    }

    public void setManufacturerPlace(String manufacturerPlace) {
        this.manufacturerPlace = manufacturerPlace;
    }

    public String getManufacturerAddress() {
        return manufacturerAddress;
    }

    public void setManufacturerAddress(String manufacturerAddress) {
        this.manufacturerAddress = manufacturerAddress;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }
}
