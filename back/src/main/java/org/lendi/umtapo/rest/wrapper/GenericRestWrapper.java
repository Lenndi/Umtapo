package org.lendi.umtapo.rest.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Generic response wrapper.
 *
 * @param <T> the type parameter
 */
public class GenericRestWrapper<T> {
    private List<T> data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer page;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalPage;

    /**
     * Gets data.
     *
     * @return the data
     */
    public List<T> getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(List<T> data) {
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
