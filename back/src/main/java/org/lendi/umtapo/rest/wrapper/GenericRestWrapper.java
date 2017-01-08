package org.lendi.umtapo.rest.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.lendi.umtapo.entity.record.simple.SimpleRecord;

import java.util.List;

/**
 * Record response wrapper.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericRestWrapper {
    private List<SimpleRecord> data;
    private Integer page;
    private Integer totalPage;

    public List<SimpleRecord> getData() {
        return data;
    }

    public void setData(List<SimpleRecord> data) {
        this.data = data;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
