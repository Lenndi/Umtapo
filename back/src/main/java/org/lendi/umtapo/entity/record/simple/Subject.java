package org.lendi.umtapo.entity.record.simple;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * Subject entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ElementCollection
    private List<String> terms;
    private String dewey;
    private String universal;

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
