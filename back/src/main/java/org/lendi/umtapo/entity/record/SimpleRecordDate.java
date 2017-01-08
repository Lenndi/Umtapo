package org.lendi.umtapo.entity.record;

import java.util.Date;

/**
 * Record date entity.
 */
public class SimpleRecordDate {
    private String publicationDate;
    private String manufactureDate;

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }
}
