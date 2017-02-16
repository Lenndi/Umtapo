package org.lendi.umtapo.solr.document;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
 * Contributor document. Based on Creator bean.
 */
@SolrDocument(solrCoreName = "record")
public class ContributorDocument {

    /**
     * The Id.
     */
    @Id
    private String id;

    @Field
    private String name;

    @Field
    private String secondName;

    @Field
    private String date;

    @Field
    private String titles;

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
}
