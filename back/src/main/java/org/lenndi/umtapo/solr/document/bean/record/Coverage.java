package org.lenndi.umtapo.solr.document.bean.record;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Coverage entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Coverage {

    private String generalNote;

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
