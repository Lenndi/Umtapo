package org.lendi.umtapo.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.lendi.umtapo.entity.Loan;
import org.lendi.umtapo.entity.ShelfMark;
import org.lendi.umtapo.entity.record.simple.SimpleRecord;
import org.lendi.umtapo.enumeration.Condition;
import org.lendi.umtapo.enumeration.ItemType;

import java.util.List;

/**
 * Item entity.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ItemDto {

    private Integer id;
    private ItemType type;
    private ShelfMark shelfmark;
    private String internalId;
    private Float purchasePrice;
    private Boolean loanable;
    private List<Loan> loan;
    private Condition condition;
    private String currency;
    private LibraryDto library;
    private SimpleRecord record;

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
    public String getInternalId() {
        return internalId;
    }

    /**
     * Sets internal id.
     *
     * @param internalId the internal id
     */
    public void setInternalId(String internalId) {
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
        return loanable;
    }

    /**
     * Sets loanable.
     *
     * @param loanable the loanable
     */
    public void setLoanable(Boolean loanable) {
        this.loanable = loanable;
    }

    /**
     * Gets loan.
     *
     * @return the loan
     */
    public List<Loan> getLoan() {
        return loan;
    }

    /**
     * Sets loan.
     *
     * @param loan the loan
     */
    public void setLoan(List<Loan> loan) {
        this.loan = loan;
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
     * Gets library id.
     *
     * @return the library id
     */
    public LibraryDto getLibrary() {
        return library;
    }

    /**
     * Sets library id.
     *
     * @param library the library id
     */
    public void setLibrary(LibraryDto library) {
        this.library = library;
    }

    /**
     * Gets record id.
     *
     * @return the record id
     */
    public SimpleRecord getRecord() {
        return record;
    }

    /**
     * Sets record id.
     *
     * @param record the record id
     */
    public void setRecord(SimpleRecord record) {
        this.record = record;
    }
}
