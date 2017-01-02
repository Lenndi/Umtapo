package org.lendi.umtapo.entity.record;

import java.util.Date;

/**
 * Right entity.
 */
public class Right {
    private String recordOrigin;
    private Date transactionDate;
    private boolean isModified;

    public String getRecordOrigin() {
        return recordOrigin;
    }

    public void setRecordOrigin(String recordOrigin) {
        this.recordOrigin = recordOrigin;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }
}
