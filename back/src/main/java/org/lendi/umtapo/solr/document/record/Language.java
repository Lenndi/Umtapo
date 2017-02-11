package org.lendi.umtapo.solr.document.record;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.solr.client.solrj.beans.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * Language entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Language {

    private static final String DOCUMENT_TYPE = "language";

    @Field(value = "document_type")
    private final String documentType;

    @Field
    private String mainLanguage;

    @Field
    private String originalLanguage;

    @Field
    private List<String> subtitles;

    @Field
    private List<String> others;

    /**
     * Instantiates a new Language.
     */
    public Language() {
        this.documentType = DOCUMENT_TYPE;
        this.subtitles = new ArrayList<>();
        this.others = new ArrayList<>();
    }

    /**
     * Gets main language.
     *
     * @return the main language
     */
    public String getMainLanguage() {
        return mainLanguage;
    }

    /**
     * Sets main language.
     *
     * @param mainLanguage the main language
     */
    public void setMainLanguage(String mainLanguage) {
        this.mainLanguage = mainLanguage;
    }

    /**
     * Gets original language.
     *
     * @return the original language
     */
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    /**
     * Sets original language.
     *
     * @param originalLanguage the original language
     */
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    /**
     * Gets subtitles.
     *
     * @return the subtitles
     */
    public List<String> getSubtitles() {
        return subtitles;
    }

    /**
     * Sets subtitles.
     *
     * @param subtitles the subtitles
     */
    public void setSubtitles(List<String> subtitles) {
        this.subtitles = subtitles;
    }

    /**
     * Add subtitle.
     *
     * @param subtitle the subtitle
     */
    public void addSubtitle(String subtitle) {
        this.subtitles.add(subtitle);
    }

    /**
     * Gets others.
     *
     * @return the others
     */
    public List<String> getOthers() {
        return others;
    }

    /**
     * Sets others.
     *
     * @param others the others
     */
    public void setOthers(List<String> others) {
        this.others = others;
    }

    /**
     * Add other.
     *
     * @param other the other
     */
    public void addOther(String other) {
        this.others.add(other);
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
