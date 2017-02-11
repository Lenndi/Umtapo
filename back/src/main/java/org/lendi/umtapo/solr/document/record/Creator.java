package org.lendi.umtapo.solr.document.record;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.solr.client.solrj.beans.Field;

/**
 * Creator entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Creator {

    private static final String DOCUMENT_TYPE = "creator";

    @Field(value = "document_type")
    private final String documentType;

    @Field
    private String name;

    @Field
    private String secondName;

    @Field
    private String date;

    @Field
    private String titles;

    /**
     * Instantiates a new Creator.
     */
    public Creator() {
        this.documentType = DOCUMENT_TYPE;
    }

    /**
     * Instantiates a new Creator.
     *
     * @param documentType the document type
     */
    public Creator(String documentType) {
        this.documentType = documentType;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets second name.
     *
     * @return the second name
     */
    public String getSecondName() {
        return secondName;
    }

    /**
     * Sets second name.
     *
     * @param secondName the second name
     */
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets titles.
     *
     * @return the titles
     */
    public String getTitles() {
        return titles;
    }

    /**
     * Sets titles.
     *
     * @param titles the titles
     */
    public void setTitles(String titles) {
        this.titles = titles;
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
