package org.lendi.umtapo.solr.document.record.simple;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.solr.client.solrj.beans.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * Record's title element.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Title {

    private static final String DOCUMENT_TYPE = "title";

    @Field(value = "document_type")
    private final String documentType;

    @Field
    private String mainTitle;

    @Field
    private String subTitle;

    @Field
    private List<String> alternateTitles;

    @Field
    private String uniformTitle;

    /**
     * Instantiates a new Title.
     */
    public Title() {
        this.documentType = DOCUMENT_TYPE;
        this.alternateTitles = new ArrayList<>();
    }

    /**
     * Gets mainTitle.
     *
     * @return the mainTitle
     */
    public String getMainTitle() {
        return mainTitle;
    }

    /**
     * Sets mainTitle.
     *
     * @param mainTitle the mainTitle
     */
    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    /**
     * Gets sub mainTitle.
     *
     * @return the sub mainTitle
     */
    public String getSubTitle() {
        return subTitle;
    }

    /**
     * Sets sub mainTitle.
     *
     * @param subTitle the sub mainTitle
     */
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    /**
     * Gets alternate titles.
     *
     * @return the alternate titles
     */
    public List<String> getAlternateTitles() {
        return alternateTitles;
    }

    /**
     * Sets alternate titles.
     *
     * @param alternateTitles the alternate titles
     */
    public void setAlternateTitles(List<String> alternateTitles) {
        this.alternateTitles = alternateTitles;
    }

    /**
     * Add alternate mainTitle.
     *
     * @param alternateTitle the alternate mainTitle
     */
    public void addAlternateTitle(String alternateTitle) {
        this.alternateTitles.add(alternateTitle);
    }

    /**
     * Gets uniform mainTitle.
     *
     * @return the uniform mainTitle
     */
    public String getUniformTitle() {
        return uniformTitle;
    }

    /**
     * Sets uniform mainTitle.
     *
     * @param uniformTitle the uniform mainTitle
     */
    public void setUniformTitle(String uniformTitle) {
        this.uniformTitle = uniformTitle;
    }

    public String getDocumentType() {
        return documentType;
    }
}
