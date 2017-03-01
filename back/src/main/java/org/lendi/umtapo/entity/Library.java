package org.lendi.umtapo.entity;

import org.lendi.umtapo.enumeration.Condition;
import org.lendi.umtapo.enumeration.ItemType;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import java.util.ArrayList;
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
    private Integer firstInternalId;
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
    @OneToMany(mappedBy = "library", cascade = CascadeType.PERSIST)
    private List<Item> items;

    /**
     * Instantiates a new Library.
     */
    public Library() {
        this.items = new ArrayList<>();
    }

    /**
     * Instantiates a new Library.
     *
     * @param name                   the name
     * @param shelfMarkNb            the shelf mark nb
     * @param useDeweyClassification the use dewey classification
     * @param subscriptionDuration   the subscription duration
     * @param borrowDuration         the borrow duration
     * @param currency               the currency
     * @param defaultZ3950           the default z 3950
     * @param borrowers              the borrowers
     */
    public Library(String name, Integer shelfMarkNb, Boolean useDeweyClassification, Integer subscriptionDuration,
                   Integer borrowDuration, String currency, Integer defaultZ3950, List<Borrower> borrowers) {
        this.items = new ArrayList<>();
        this.name = name;
        this.shelfMarkNb = shelfMarkNb;
        this.useDeweyClassification = useDeweyClassification;
        this.subscriptionDuration = subscriptionDuration;
        this.borrowDuration = borrowDuration;
        this.currency = currency;
        this.defaultZ3950 = defaultZ3950;
        this.borrowers = borrowers;
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
     * Gets borrowers.
     *
     * @return the borrowers
     */
    public List<Borrower> getBorrowers() {
        return borrowers;
    }

    /**
     * Sets borrowers.
     *
     * @param borrowers the borrowers
     */
    public void setBorrowers(List<Borrower> borrowers) {
        this.borrowers = borrowers;
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
     * Gets subscription.
     *
     * @return the subscription
     */
    public List<Subscription> getSubscription() {
        return subscription;
    }

    /**
     * Sets subscription.
     *
     * @param subscription the subscription
     */
    public void setSubscription(List<Subscription> subscription) {
        this.subscription = subscription;
    }

    /**
     * Gets items.
     *
     * @return the items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Sets items.
     *
     * @param items the items
     */
    public void setItems(List<Item> items) {
        this.items = items;
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

    /**
     * Create the first item to initialize the first internalId in Item table.
     */
    @PrePersist
    public void createFirstItem() {
        if (this.firstInternalId != null) {
            Item item = new Item();
            item.setInternalId(this.firstInternalId);
            item.setLibrary(this);
            item.setLoanable(false);
            item.setType(ItemType.INITIAL_ITEM);
            item.setBorrowed(false);
            item.setCondition(Condition.SOLD);

            items.add(item);
        }
    }
}
