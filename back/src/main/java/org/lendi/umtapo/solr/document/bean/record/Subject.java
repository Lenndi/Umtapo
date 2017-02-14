package org.lendi.umtapo.solr.document.bean.record;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Subject entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Subject {

    private List<String> terms;
    private String dewey;
    private String universal;

    /**
     * Instantiates a new Subject.
     */
    public Subject() {
        this.terms = new ArrayList<>();
    }

    /**
     * Gets terms.
     *
     * @return the terms
     */
    public List<String> getTerms() {
        return terms;
    }

    /**
     * Sets terms.
     *
     * @param terms the terms
     */
    public void setTerms(List<String> terms) {
        this.terms = terms;
    }

    /**
     * Add term.
     *
     * @param term the term
     */
    public void addTerm(String term) {
        this.terms.add(term);
    }

    /**
     * Gets dewey.
     *
     * @return the dewey
     */
    public String getDewey() {
        return dewey;
    }

    /**
     * Sets dewey.
     *
     * @param dewey the dewey
     */
    public void setDewey(String dewey) {
        this.dewey = dewey;
    }

    /**
     * Gets universal.
     *
     * @return the universal
     */
    public String getUniversal() {
        return universal;
    }

    /**
     * Sets universal.
     *
     * @param universal the universal
     */
    public void setUniversal(String universal) {
        this.universal = universal;
    }
}
