package org.lendi.umtapo.entity.record;

import java.util.ArrayList;
import java.util.List;

/**
 * Description entity.
 */
public class Description {
    private String mainDescription;
    private List<String> otherDescriptions;
    private String mainPhysicalDescription;
    private String secondaryPhysicalDescription;
    private String format;
    private String associatedMaterial;

    public Description() {
        this.otherDescriptions = new ArrayList<>();
    }

    public String getMainDescription() {
        return mainDescription;
    }

    public void setMainDescription(String mainDescription) {
        this.mainDescription = mainDescription;
    }

    public List<String> getOtherDescriptions() {
        return otherDescriptions;
    }

    public void setOtherDescriptions(List<String> otherDescriptions) {
        this.otherDescriptions = otherDescriptions;
    }

    public void addOtherDescription(String description) {
        this.otherDescriptions.add(description);
    }

    public String getMainPhysicalDescription() {
        return mainPhysicalDescription;
    }

    public void setMainPhysicalDescription(String mainPhysicalDescription) {
        this.mainPhysicalDescription = mainPhysicalDescription;
    }

    public String getSecondaryPhysicalDescription() {
        return secondaryPhysicalDescription;
    }

    public void setSecondaryPhysicalDescription(String secondaryPhysicalDescription) {
        this.secondaryPhysicalDescription = secondaryPhysicalDescription;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getAssociatedMaterial() {
        return associatedMaterial;
    }

    public void setAssociatedMaterial(String associatedMaterial) {
        this.associatedMaterial = associatedMaterial;
    }
}
