package org.lendi.umtapo.entity.record;

import java.util.List;

/**
 * Extends a record.
 */
public class RecordListWrapper<T> {
    private List<T> record;
    private int hitCount;

    public List<T> getRecord() {
        return record;
    }

    public void setRecord(List<T> record) {
        this.record = record;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }
}
