package org.lendi.umtapo.solr.document.record.simple;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Right entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Right {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String recordOrigin;
    private String transactionDate;
    private boolean isModified;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
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
}
