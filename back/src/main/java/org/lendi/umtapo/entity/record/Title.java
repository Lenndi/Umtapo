package org.lendi.umtapo.entity.record;

import java.util.ArrayList;
import java.util.List;

/**
 * Record's title element.
 */
public class Title {
    private String title;
    private String subTitle;
    private List<String> alternateTitles;
    private String uniformTitle;

    public Title () {
        this.alternateTitles = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public List<String> getAlternateTitles() {
        return alternateTitles;
    }

    public void setAlternateTitles(List<String> alternateTitles) {
        this.alternateTitles = alternateTitles;
    }

    public void addAlternateTitle(String alternateTitle) {
        this.alternateTitles.add(alternateTitle);
    }

    public String getUniformTitle() {
        return uniformTitle;
    }

    public void setUniformTitle(String uniformTitle) {
        this.uniformTitle = uniformTitle;
    }
}
