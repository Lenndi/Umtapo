package org.lendi.umtapo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * The type Borrower.
 */
@Entity
public class Borrower {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String comment;
    private ZonedDateTime birthday;
    private Integer quota;
    private Boolean emailOptin;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "BORROWER_SUBSCRIPTION",
            joinColumns = @JoinColumn(name = "BORROWER_ID"),
            inverseJoinColumns = @JoinColumn(name = "SUBSCRIPTION_ID")
    )
    private List<Subscription> subscriptions;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Library library;

    /**
     * Instantiates a new Borrower.
     */
    public Borrower() {
    }

    /**
     * Instantiates a new Borrower.
     *
     * @param name          the name
     * @param comment       the comment
     * @param birthday      the birthday
     * @param quota         the quota
     * @param emailOptin    the email optin
     * @param address       the address
     * @param subscriptions the subscriptions
     * @param loans         the loans
     * @param library       the library
     */
    public Borrower(
            String name, String comment, ZonedDateTime birthday, Integer quota, Boolean emailOptin, Address address,
            List<Subscription> subscriptions, Library library) {
        this.name = name;
        this.comment = comment;
        this.birthday = birthday;
        this.quota = quota;
        this.emailOptin = emailOptin;
        this.address = address;
        this.subscriptions = subscriptions;
        this.library = library;
    }

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
     * Gets library.
     *
     * @return the library
     */
    public Library getLibrary() {
        return library;
    }

    /**
     * Sets library.
     *
     * @param library the library
     */
    public void setLibrary(Library library) {
        this.library = library;
    }

    /**
     * Gets subscriptions.
     *
     * @return the subscriptions
     */
    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    /**
     * Sets subscriptions.
     *
     * @param subscriptions the subscriptions
     */
    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(Address address) {
        this.address = address;
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
}
