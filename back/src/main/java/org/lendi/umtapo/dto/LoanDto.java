package org.lendi.umtapo.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.ZonedDateTime;


/**
 * Loan dto.
 * <p>
 * Created by axel on 29/11/16.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "idIdentify")
public class LoanDto {

    private Integer id;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private BorrowerDto borrower;
    private boolean isReturned;
    private ItemDto item;

    /**
     * Instantiates a new Loan dto.
     */
    public LoanDto() {
    }

    /**
     * Instantiates a new Loan dto.
     *
     * @param id         the id
     * @param start      the start
     * @param end        the end
     * @param borrower   the borrower
     * @param isReturned the is returned
     * @param item       the item
     */
    public LoanDto(Integer id, ZonedDateTime start, ZonedDateTime end, BorrowerDto borrower, boolean isReturned, ItemDto item) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.borrower = borrower;
        this.isReturned = isReturned;
        this.item = item;
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
     * Is returned boolean.
     *
     * @return the boolean
     */
    public boolean isReturned() {
        return isReturned;
    }

    /**
     * Sets returned.
     *
     * @param returned the returned
     */
    public void setReturned(boolean returned) {
        isReturned = returned;
    }

    /**
     * Gets start.
     *
     * @return the start
     */
    public ZonedDateTime getStart() {
        return start;
    }

    /**
     * Sets start.
     *
     * @param start the start
     */
    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    /**
     * Gets end.
     *
     * @return the end
     */
    public ZonedDateTime getEnd() {
        return end;
    }

    /**
     * Sets end.
     *
     * @param end the end
     */
    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    /**
     * Gets borrower.
     *
     * @return the borrower
     */
    public BorrowerDto getBorrower() {
        return borrower;
    }

    /**
     * Sets borrower.
     *
     * @param borrower the borrower
     */
    public void setBorrower(BorrowerDto borrower) {
        this.borrower = borrower;
    }

    /**
     * Gets item.
     *
     * @return the item
     */
    public ItemDto getItem() {
        return item;
    }

    /**
     * Sets item.
     *
     * @param item the item
     */
    public void setItem(ItemDto item) {
        this.item = item;
    }
}
