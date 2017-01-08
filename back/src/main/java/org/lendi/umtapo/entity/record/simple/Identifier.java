package org.lendi.umtapo.entity.record.simple;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Identifier entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Identifier {
    public static final String ISBN = "ISBN";
    public static final String ISSN = "ISSN";
    public static final String ISMN = "ISMN";
    public static final String ISRN = "ISRN";
    public static final String ISRC = "ISRC";

    private String recordIdentifier;
    private String serialNumber;
    private String serialType;
    private String barCode;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getRecordIdentifier() {
        return recordIdentifier;
    }

    public void setRecordIdentifier(String recordIdentifier) {
        this.recordIdentifier = recordIdentifier;
    }

    public String getSerialType() {
        return serialType;
    }

    public void setSerialType(String serialType) {
        this.serialType = serialType;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
