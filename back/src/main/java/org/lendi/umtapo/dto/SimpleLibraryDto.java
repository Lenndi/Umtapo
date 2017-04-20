package org.lendi.umtapo.dto;

/**
 * Library entity Dto.
 * <p>
 * Created by axel on 29/11/16.
 */
public class SimpleLibraryDto {

    private Integer id;
    private Integer firstInternalId;
    private String name;
    private Boolean external;
    private Integer shelfMarkNb;
    private Boolean useDeweyClassification;
    private Integer subscriptionDuration;
    private Integer borrowDuration;
    private String currency;
    private Integer defaultZ3950;

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
     * Gets external.
     *
     * @return the external
     */
    public Boolean getExternal() {
        return external;
    }

    /**
     * Sets external.
     *
     * @param external the external
     */
    public void setExternal(Boolean external) {
        this.external = external;
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
     * Gets first internal id.
     *
     * @return the first internal id
     */
    public Integer getFirstInternalId() {
        return firstInternalId;
    }

    /**
     * Sets first internal id.
     *
     * @param firstInternalId the first internal id
     */
    public void setFirstInternalId(Integer firstInternalId) {
        this.firstInternalId = firstInternalId;
    }
}
