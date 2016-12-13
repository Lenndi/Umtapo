package org.lendi.umtapo.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.List;

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
    private Integer subscriptionDuration;
    private Integer borrowDuration;
    private String currency;
    private Integer defaultZ3950;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<BorrowerDto> borrowers;
    private List<SubscriptionDto> subscriptions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getSubscriptionDuration() {
        return subscriptionDuration;
    }

    public void setSubscriptionDuration(Integer subscriptionDuration) {
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

    public List<BorrowerDto> getBorrowers() {
        return borrowers;
    }

    public void setBorrowers(List<BorrowerDto> borrowers) {
        this.borrowers = borrowers;
    }

    public List<SubscriptionDto> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<SubscriptionDto> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
