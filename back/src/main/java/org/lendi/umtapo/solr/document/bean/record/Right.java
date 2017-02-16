package org.lendi.umtapo.solr.document.bean.record;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Right entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Right {

    private String recordOrigin;
    private String transactionDate;
    private boolean isModified;

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
    public boolean getIsModified() {
        return isModified;
    }

    /**
     * Sets modified.
     *
     * @param modified the modified
     */
    public void setIsModified(boolean modified) {
        isModified = modified;
    }
}
