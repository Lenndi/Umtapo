package org.lendi.umtapo.solr.document.record;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.solr.client.solrj.beans.Field;

/**
 * Coverage entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Coverage {

    private static final String DOCUMENT_TYPE = "coverage";

    @Field(value = "document_type")
    private final String documentType;

    @Field
    private String generalNote;

    /**
     * Instantiates a new Coverage.
     */
    public Coverage() {
        this.documentType = DOCUMENT_TYPE;
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

    /**
     * Gets document type.
     *
     * @return the document type
     */
    public String getDocumentType() {
        return documentType;
    }
}
