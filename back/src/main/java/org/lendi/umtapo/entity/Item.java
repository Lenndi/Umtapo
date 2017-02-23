package org.lendi.umtapo.entity;

import org.lendi.umtapo.enumeration.Condition;
import org.lendi.umtapo.enumeration.ItemType;
import org.lendi.umtapo.solr.document.bean.record.Record;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.util.List;

/**
 * Item entity.
 * <p>
 * Created by axel on 29/11/16.
 */
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private ItemType type;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private ShelfMark shelfmark;
    @Column(unique = true)
    private Integer internalId;
    private Integer purchasePrice;
    private Boolean isLoanable;
    private Boolean isBorrowed;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Loan> loans;
    @Enumerated(EnumType.STRING)
    private Condition condition;
    private String currency;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Library library;
    private String recordId;
    @Transient
    private Record record;

    /**
     * Instantiates a new Item.
     */
    public Item() {
    }

    /**
     * Instantiates a new Item.
     *
     * @param type          the type
     * @param shelfmark     the shelfmark
     * @param internalId    the internal id
     * @param purchasePrice the purchase price
     * @param isLoanable    the isLoanable
     * @param loans         the loans
     * @param condition     the condition
     * @param currency      the currency
     * @param library       the library
     * @param isBorrowed    the is borrowed
     */
    public Item(ItemType type, ShelfMark shelfmark, Integer internalId, Integer purchasePrice, Boolean isLoanable,
                List<Loan> loans, Condition condition, String currency, Library library, Boolean isBorrowed) {
        this.type = type;
        this.shelfmark = shelfmark;
        this.internalId = internalId;
        this.purchasePrice = purchasePrice;
        this.isLoanable = isLoanable;
        this.loans = loans;
        this.condition = condition;
        this.isBorrowed = isBorrowed;
        this.currency = currency;
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
     * Is borrowed boolean.
     *
     * @return the boolean
     */
    public Boolean isBorrowed() {
        return isBorrowed;
    }

    /**
     * Sets borrowed.
     *
     * @param borrowed the borrowed
     */
    public void setBorrowed(Boolean borrowed) {
        isBorrowed = borrowed;
    }

    /**
     * Gets loans.
     *
     * @return the loans
     */
    public List<Loan> getLoans() {
        return loans;
    }

    /**
     * Sets loans.
     *
     * @param loans the loans
     */
    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    /**
     * Gets condition.
     *
     * @return the condition
     */
    public Condition getCondition() {
        return condition;
    }

    /**
     * Sets condition.
     *
     * @param condition the condition
     */
    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    /**
     * Gets loans.
     *
     * @return the loans
     */
    public List<Loan> getLoan() {
        return loans;
    }

    /**
     * Sets loans.
     *
     * @param loanList the loans
     */
    public void setLoan(List<Loan> loanList) {
        this.loans = loanList;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public ItemType getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(ItemType type) {
        this.type = type;
    }

    /**
     * Gets internal id.
     *
     * @return the internal id
     */
    public Integer getInternalId() {
        return internalId;
    }

    /**
     * Sets internal id.
     *
     * @param internalId the internal id
     */
    public void setInternalId(Integer internalId) {
        this.internalId = internalId;
    }

    /**
     * Gets purchase price.
     *
     * @return the purchase price
     */
    public Integer getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * Sets purchase price.
     *
     * @param purchasePrice the purchase price
     */
    public void setPurchasePrice(Integer purchasePrice) {
        this.purchasePrice = purchasePrice;
    }


    /**
     * Gets shelfmark.
     *
     * @return the shelfmark
     */
    public ShelfMark getShelfmark() {
        return shelfmark;
    }

    /**
     * Sets shelfmark.
     *
     * @param shelfmark the shelfmark
     */
    public void setShelfmark(ShelfMark shelfmark) {
        this.shelfmark = shelfmark;
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
     * Gets record.
     *
     * @return the record
     */
    public Record getRecord() {
        return record;
    }

    /**
     * Sets record.
     *
     * @param record the record
     */
    public void setRecord(Record record) {
        this.record = record;
    }

    /**
     * Gets record id.
     *
     * @return the record id
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * Sets record id.
     *
     * @param recordId the record id
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    /**
     * Gets loanable.
     *
     * @return the loanable
     */
    public Boolean getLoanable() {
        return isLoanable;
    }

    /**
     * Sets loanable.
     *
     * @param loanable the loanable
     */
    public void setLoanable(Boolean loanable) {
        isLoanable = loanable;
    }

    /**
     * Gets borrowed.
     *
     * @return the borrowed
     */
    public Boolean getBorrowed() {
        return isBorrowed;
    }
}
