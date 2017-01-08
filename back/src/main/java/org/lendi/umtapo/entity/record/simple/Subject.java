package org.lendi.umtapo.entity.record.simple;

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

    public Subject() {
        this.terms = new ArrayList<>();
    }

    public List<String> getTerms() {
        return terms;
    }

    public void setTerms(List<String> terms) {
        this.terms = terms;
    }

    public void addTerm(String term) {
        this.terms.add(term);
    }

    public String getDewey() {
        return dewey;
    }

    public void setDewey(String dewey) {
        this.dewey = dewey;
    }

    public String getUniversal() {
        return universal;
    }

    public void setUniversal(String universal) {
        this.universal = universal;
    }
}
