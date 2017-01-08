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

    /**
     * Gets data.
     *
     * @return the data
     */
    public List<SimpleRecord> getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(List<SimpleRecord> data) {
        this.data = data;
    }

    /**
     * Gets page.
     *
     * @return the page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * Sets page.
     *
     * @param page the page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * Gets total page.
     *
     * @return the total page
     */
    public Integer getTotalPage() {
        return totalPage;
    }

    /**
     * Sets total page.
     *
     * @param totalPage the total page
     */
    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
