package org.lendi.umtapo.entity;

import javax.persistence.*;
import java.util.List;


/**
 * Library entity.
 * <p>
 * Created by axel on 29/11/16.
 */
@Entity
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private Integer shelfMarkNb;
    private Boolean useDeweyClassification;
    private Integer subscriptionDuration;
    private Integer borrowDuration;
    private String currency;
    private Integer defaultZ3950;
    @OneToMany(mappedBy = "library")
    private List<Subscription> subscription;
    @OneToMany(mappedBy = "library")
    private List<Borrower> borrowers;

    public Library() {
    }

    public Library(String name, Integer shelfMarkNb, Boolean useDeweyClassification, Integer subscriptionDuration, Integer borrowDuration, String currency, Integer defaultZ3950, List<Borrower> borrowers) {
        this.name = name;
        this.shelfMarkNb = shelfMarkNb;
        this.useDeweyClassification = useDeweyClassification;
        this.subscriptionDuration = subscriptionDuration;
        this.borrowDuration = borrowDuration;
        this.currency = currency;
        this.defaultZ3950 = defaultZ3950;
        this.borrowers = borrowers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Borrower> getBorrowers() {
        return borrowers;
    }

    public void setBorrowers(List<Borrower> borrowers) {
        this.borrowers = borrowers;
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

    public List<Subscription> getSubscription() {
        return subscription;
    }

    public void setSubscription(List<Subscription> subscription) {
        this.subscription = subscription;
    }
}
