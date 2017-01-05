package org.lendi.umtapo.entity.record;

import java.util.ArrayList;
import java.util.List;

/**
 * Language entity.
 */
public class Language {
    private String mainLanguage;
    private String originalLanguage;
    private List<String> subtitles;
    private List<String> others;

    public Language() {
        this.subtitles = new ArrayList<>();
        this.others = new ArrayList<>();
    }

    public String getMainLanguage() {
        return mainLanguage;
    }

    public void setMainLanguage(String mainLanguage) {
        this.mainLanguage = mainLanguage;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public List<String> getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(List<String> subtitles) {
        this.subtitles = subtitles;
    }

    public void addSubtitle(String subtitle) {
        this.subtitles.add(subtitle);
    }

    public List<String> getOthers() {
        return others;
    }

    public void setOthers(List<String> others) {
        this.others = others;
    }

    public void addOther(String other) {
        this.others.add(other);
    }
}
