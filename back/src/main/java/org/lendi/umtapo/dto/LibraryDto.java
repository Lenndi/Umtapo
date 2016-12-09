package org.lendi.umtapo.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Library entity Dto.
 * <p>
 * Created by axel on 29/11/16.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class LibraryDto {

    private Integer id;
    private String name;
    private Integer shelfMarkNb;
    private Boolean useDeweyClassification;
    private Boolean subscriptionDuration;
    private Integer borrowDuration;
    private String currency;
    private Integer defaultZ3950;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getShelfMarkNb() {
        return shelfMarkNb;
    }

    public void setShelfMarkNb(Integer shelfMarkNb) {
        this.shelfMarkNb = shelfMarkNb;
    }

    public Boolean getUseDeweyClassification() {
        return useDeweyClassification;
    }

    public void setUseDeweyClassification(Boolean useDeweyClassification) {
        this.useDeweyClassification = useDeweyClassification;
    }

    public Boolean getSubscriptionDuration() {
        return subscriptionDuration;
    }

    public void setSubscriptionDuration(Boolean subscriptionDuration) {
        this.subscriptionDuration = subscriptionDuration;
    }

    public Integer getBorrowDuration() {
        return borrowDuration;
    }

    public void setBorrowDuration(Integer borrowDuration) {
        this.borrowDuration = borrowDuration;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getDefaultZ3950() {
        return defaultZ3950;
    }

    public void setDefaultZ3950(Integer defaultZ3950) {
        this.defaultZ3950 = defaultZ3950;
    }
}
