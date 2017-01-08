package org.lendi.umtapo.entity.record.simple;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Record's title element.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Title {
    private String title;
    private String subTitle;
    private List<String> alternateTitles;
    private String uniformTitle;

    /**
     * Instantiates a new Title.
     */
    public Title() {
        this.alternateTitles = new ArrayList<>();
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets sub title.
     *
     * @return the sub title
     */
    public String getSubTitle() {
        return subTitle;
    }

    /**
     * Sets sub title.
     *
     * @param subTitle the sub title
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
     * Add alternate title.
     *
     * @param alternateTitle the alternate title
     */
    public void addAlternateTitle(String alternateTitle) {
        this.alternateTitles.add(alternateTitle);
    }

    /**
     * Gets uniform title.
     *
     * @return the uniform title
     */
    public String getUniformTitle() {
        return uniformTitle;
    }

    /**
     * Sets uniform title.
     *
     * @param uniformTitle the uniform title
     */
    public void setUniformTitle(String uniformTitle) {
        this.uniformTitle = uniformTitle;
    }
}
