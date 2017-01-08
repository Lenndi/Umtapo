package org.lendi.umtapo.entity.record.simple;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Coverage entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Coverage {
    private String generalNote;

    public String getGeneralNote() {
        return generalNote;
    }

    public void setGeneralNote(String generalNote) {
        this.generalNote = generalNote;
    }
}
