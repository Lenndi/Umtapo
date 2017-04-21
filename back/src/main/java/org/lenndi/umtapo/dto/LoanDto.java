package org.lenndi.umtapo.dto;

import java.time.ZonedDateTime;

/**
 * Loan dto.
 * <p>
 * Created by axel on 29/11/16.
 */
public class LoanDto {

    private Integer id;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private SimpleBorrowerDto borrower;
    private Boolean returned;
    private LoanItemDto item;

    /**
     * Instantiates a new Loan dto.
     */
    public LoanDto() {
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
    public SimpleBorrowerDto getBorrower() {
        return borrower;
    }

    /**
     * Sets borrower.
     *
     * @param borrower the borrower
     */
    public void setBorrower(SimpleBorrowerDto borrower) {
        this.borrower = borrower;
    }

    /**
     * Gets item.
     *
     * @return the item
     */
    public LoanItemDto getItem() {
        return item;
    }

    /**
     * Sets item.
     *
     * @param item the item
     */
    public void setItem(LoanItemDto item) {
        this.item = item;
    }
}
