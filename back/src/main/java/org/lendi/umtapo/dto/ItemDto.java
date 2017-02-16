package org.lendi.umtapo.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.lendi.umtapo.entity.ShelfMark;
import org.lendi.umtapo.enumeration.Condition;
import org.lendi.umtapo.enumeration.ItemType;
import org.lendi.umtapo.solr.document.bean.record.Record;

import java.util.List;

/**
 * The type Item dto.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "idIdentify")
public class ItemDto {

    private Integer id;
    private ItemType type;
    private ShelfMark shelfmark;
    private Integer internalId;
    private Float purchasePrice;
    private Boolean isLoanable;
    private Boolean isBorrowed;
    private List<LoanDto> loans;
    private Condition condition;
    private String currency;
    private LibraryDto library;
    private String recordId;
    private Record record;

    /**
     * Instantiates a new Item dto.
     */
    public ItemDto() {
    }

    /**
     * Instantiates a new Item dto.
     *
     * @param id            the id
     * @param type          the type
     * @param shelfmark     the shelfmark
     * @param internalId    the internal id
     * @param purchasePrice the purchase price
     * @param isLoanable    the isLoanable
     * @param loans         the loans
     * @param condition     the condition
     * @param currency      the currency
     * @param library       the library
     * @param record        the record
     * @param isBorrowed    the is borrowed
     */
    public ItemDto(Integer id, ItemType type, ShelfMark shelfmark, Integer internalId, Float purchasePrice,
                   Boolean isLoanable, List<LoanDto> loans, Condition condition, String currency, LibraryDto library,
                   Record record, boolean isBorrowed) {
        this.id = id;
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
        this.record = record;
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
     * Gets borrowed.
     *
     * @return the borrowed
     */
    public Boolean getBorrowed() {
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
    public Float getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * Sets purchase price.
     *
     * @param purchasePrice the purchase price
     */
    public void setPurchasePrice(Float purchasePrice) {
        this.purchasePrice = purchasePrice;
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
     * Gets loans.
     *
     * @return the loans
     */
    public List<LoanDto> getLoans() {
        return loans;
    }

    /**
     * Sets loans.
     *
     * @param loans the loans
     */
    public void setLoans(List<LoanDto> loans) {
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
    public LibraryDto getLibrary() {
        return library;
    }

    /**
     * Sets library.
     *
     * @param library the library
     */
    public void setLibrary(LibraryDto library) {
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
}
