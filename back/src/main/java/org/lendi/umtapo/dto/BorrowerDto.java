package org.lendi.umtapo.dto;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Dto borrower object
 * <p>
 * Created by axel on 29/11/16.
 */
public class BorrowerDto {

    private Integer id;
    private String name;
    private String comment;
    private ZonedDateTime birthday;
    private Integer quota;
    private Boolean emailOptin;
    private AddressDto address;
    private List<SubscriptionDto> subscriptions;
    private Integer libraryId;
    private String libraryName;
    private Integer nbLoans;
    private Boolean tooMuchLoans;
    private Boolean lateness;
    private String nfcId;
    private ZonedDateTime subscriptionStart;
    private ZonedDateTime subscriptionEnd;

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
     * Gets birthday.
     *
     * @return the birthday
     */
    public ZonedDateTime getBirthday() {
        return birthday;
    }

    /**
     * Sets birthday.
     *
     * @param birthday the birthday
     */
    public void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public AddressDto getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(AddressDto address) {
        this.address = address;
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
     * Gets comment.
     *
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets comment.
     *
     * @param comment the comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets quota.
     *
     * @return the quota
     */
    public Integer getQuota() {
        return quota;
    }

    /**
     * Sets quota.
     *
     * @param quota the quota
     */
    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    /**
     * Gets email optin.
     *
     * @return the email optin
     */
    public Boolean getEmailOptin() {
        return emailOptin;
    }

    /**
     * Sets email optin.
     *
     * @param emailOptin the email optin
     */
    public void setEmailOptin(Boolean emailOptin) {
        this.emailOptin = emailOptin;
    }

    /**
     * Gets nb loans.
     *
     * @return the nb loans
     */
    public Integer getNbLoans() {
        return nbLoans;
    }

    /**
     * Sets nb loans.
     *
     * @param nbLoans the nb loans
     */
    public void setNbLoans(Integer nbLoans) {
        this.nbLoans = nbLoans;
    }

    /**
     * Is too much loans boolean.
     *
     * @return the boolean
     */
    public Boolean isTooMuchLoans() {
        return tooMuchLoans;
    }

    /**
     * Sets too much loans.
     *
     * @param tooMuchLoans the too much loans
     */
    public void setTooMuchLoans(Boolean tooMuchLoans) {
        this.tooMuchLoans = tooMuchLoans;
    }

    /**
     * Gets lateness.
     *
     * @return the lateness
     */
    public Boolean getLateness() {
        return lateness;
    }

    /**
     * Sets lateness.
     *
     * @param lateness the lateness
     */
    public void setLateness(Boolean lateness) {
        this.lateness = lateness;
    }

    /**
     * Gets subscription start.
     *
     * @return the subscription start
     */
    public ZonedDateTime getSubscriptionStart() {
        return subscriptionStart;
    }

    /**
     * Sets subscription start.
     *
     * @param subscriptionStart the subscription start
     */
    public void setSubscriptionStart(ZonedDateTime subscriptionStart) {
        this.subscriptionStart = subscriptionStart;
    }

    /**
     * Gets subscription end.
     *
     * @return the subscription end
     */
    public ZonedDateTime getSubscriptionEnd() {
        return subscriptionEnd;
    }

    /**
     * Sets subscription end.
     *
     * @param subscriptionEnd the subscription end
     */
    public void setSubscriptionEnd(ZonedDateTime subscriptionEnd) {
        this.subscriptionEnd = subscriptionEnd;
    }

    /**
     * Gets library id.
     *
     * @return the library id
     */
    public Integer getLibraryId() {
        return libraryId;
    }

    /**
     * Sets library id.
     *
     * @param libraryId the library id
     */
    public void setLibraryId(Integer libraryId) {
        this.libraryId = libraryId;
    }

    /**
     * Gets library name.
     *
     * @return the library name
     */
    public String getLibraryName() {
        return libraryName;
    }

    /**
     * Sets library name.
     *
     * @param libraryName the library name
     */
    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    /**
     * Gets too much loans.
     *
     * @return the too much loans
     */
    public Boolean getTooMuchLoans() {
        return tooMuchLoans;
    }

    /**
     * Gets nfc id.
     *
     * @return the nfc id
     */
    public String getNfcId() {
        return nfcId;
    }

    /**
     * Sets nfc id.
     *
     * @param nfcId the nfc id
     */
    public void setNfcId(String nfcId) {
        this.nfcId = nfcId;
    }
}
