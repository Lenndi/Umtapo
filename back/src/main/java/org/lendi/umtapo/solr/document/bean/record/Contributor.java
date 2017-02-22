package org.lendi.umtapo.solr.document.bean.record;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The type Contributor.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contributor extends Creator {

    /**
     * The Id.
     */
    private String id;

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }
}
