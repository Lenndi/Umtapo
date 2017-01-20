package org.lendi.umtapo.entity.record.simple;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * Language entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String mainLanguage;
    private String originalLanguage;
    @ElementCollection
    private List<String> subtitles;
    @ElementCollection
    private List<String> others;

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
     * Instantiates a new Language.
     */
    public Language() {
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
}
