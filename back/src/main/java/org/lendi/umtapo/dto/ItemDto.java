package org.lendi.umtapo.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.lendi.umtapo.entity.Loan;
import org.lendi.umtapo.enumeration.Condition;

import java.util.List;

/**
 * Item entity.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ItemDto {

    private Integer id;
    private String type;
    private String internalId;
    private Integer purchasePrice;
    private Boolean loanable;
    private List<Loan> loan;
    private Condition condition;

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
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
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
}
