package org.lendi.umtapo.dto;

import java.util.List;


/**
 * Library entity Dto.
 * <p>
 * Created by axel on 29/11/16.
 */
public class LibraryDto {

    private Integer id;
    private String name;
    private Integer shelfMarkNb;
    private Boolean useDeweyClassification;
    private Boolean subscriptionDuration;
    private Integer borrowDuration;
    private String currency;
    private List<BorrowerDto> borrowers;


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

    public List<BorrowerDto> getBorrowers() {
        return borrowers;
    }

    public void setBorrowers(List<BorrowerDto> borrowers) {
        this.borrowers = borrowers;
    }
}
