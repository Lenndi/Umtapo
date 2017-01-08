package org.lendi.umtapo.entity.record;

import java.util.List;

/**
 * Extends a record.
 *
 * @param <T> the type parameter
 */
public class RecordListWrapper<T> {
    private List<T> record;
    private int hitCount;

    /**
     * Gets record.
     *
     * @return the record
     */
    public List<T> getRecord() {
        return record;
    }

    /**
     * Sets record.
     *
     * @param record the record
     */
    public void setRecord(List<T> record) {
        this.record = record;
    }

    /**
     * Gets hit count.
     *
     * @return the hit count
     */
    public int getHitCount() {
        return hitCount;
    }

    /**
     * Sets hit count.
     *
     * @param hitCount the hit count
     */
    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }
}
