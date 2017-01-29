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
    private Boolean returned;
    private ItemDto item;

    /**
     * Instantiates a new Loan dto.
     */
    public LoanDto() {
    }

    /**
     * Instantiates a new Loan dto.
     *
     * @param id       the id
     * @param start    the start
     * @param end      the end
     * @param borrower the borrower
     * @param returned the returned
     * @param item     the item
     */
    public LoanDto(Integer id, ZonedDateTime start, ZonedDateTime end, BorrowerDto borrower, Boolean returned, ItemDto item) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.borrower = borrower;
        this.returned = returned;
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
     * Gets returned.
     *
     * @return the returned
     */
    public Boolean getReturned() {
        return returned;
    }

    /**
     * Sets returned.
     *
     * @param returned the returned
     */
    public void setReturned(Boolean returned) {
        this.returned = returned;
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
