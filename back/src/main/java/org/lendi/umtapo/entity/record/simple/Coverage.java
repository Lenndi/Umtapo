package org.lendi.umtapo.entity.record.simple;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Coverage entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Coverage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String generalNote;

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
     * Gets general note.
     *
     * @return the general note
     */
    public String getGeneralNote() {
        return generalNote;
    }

    /**
     * Sets general note.
     *
     * @param generalNote the general note
     */
    public void setGeneralNote(String generalNote) {
        this.generalNote = generalNote;
    }
}
