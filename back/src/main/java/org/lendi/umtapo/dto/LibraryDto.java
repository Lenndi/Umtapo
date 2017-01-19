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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets shelf mark nb.
     *
     * @return the shelf mark nb
     */
    public Integer getShelfMarkNb() {
        return shelfMarkNb;
    }

    /**
     * Sets shelf mark nb.
     *
     * @param shelfMarkNb the shelf mark nb
     */
    public void setShelfMarkNb(Integer shelfMarkNb) {
        this.shelfMarkNb = shelfMarkNb;
    }

    /**
     * Gets use dewey classification.
     *
     * @return the use dewey classification
     */
    public Boolean getUseDeweyClassification() {
        return useDeweyClassification;
    }

    /**
     * Sets use dewey classification.
     *
     * @param useDeweyClassification the use dewey classification
     */
    public void setUseDeweyClassification(Boolean useDeweyClassification) {
        this.useDeweyClassification = useDeweyClassification;
    }

    /**
     * Gets subscription duration.
     *
     * @return the subscription duration
     */
    public Integer getSubscriptionDuration() {
        return subscriptionDuration;
    }

    /**
     * Sets subscription duration.
     *
     * @param subscriptionDuration the subscription duration
     */
    public void setSubscriptionDuration(Integer subscriptionDuration) {
        this.subscriptionDuration = subscriptionDuration;
    }

    /**
     * Gets borrow duration.
     *
     * @return the borrow duration
     */
    public Integer getBorrowDuration() {
        return borrowDuration;
    }

    /**
     * Sets borrow duration.
     *
     * @param borrowDuration the borrow duration
     */
    public void setBorrowDuration(Integer borrowDuration) {
        this.borrowDuration = borrowDuration;
    }

    /**
     * Gets currency.
     *
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets currency.
     *
     * @param currency the currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Gets default z 3950.
     *
     * @return the default z 3950
     */
    public Integer getDefaultZ3950() {
        return defaultZ3950;
    }

    /**
     * Sets default z 3950.
     *
     * @param defaultZ3950 the default z 3950
     */
    public void setDefaultZ3950(Integer defaultZ3950) {
        this.defaultZ3950 = defaultZ3950;
    }

    /**
     * Gets borrowers.
     *
     * @return the borrowers
     */
    public List<BorrowerDto> getBorrowers() {
        return borrowers;
    }

    /**
     * Sets borrowers.
     *
     * @param borrowers the borrowers
     */
    public void setBorrowers(List<BorrowerDto> borrowers) {
        this.borrowers = borrowers;
    }

    /**
     * Gets subscriptions.
     *
     * @return the subscriptions
     */
    public List<SubscriptionDto> getSubscriptions() {
        return subscriptions;
    }

    /**
     * Sets subscriptions.
     *
     * @param subscriptions the subscriptions
     */
    public void setSubscriptions(List<SubscriptionDto> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
