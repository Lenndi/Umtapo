package org.lendi.umtapo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import java.time.ZonedDateTime;

/**
 * Loan entity.
 * <p>
 * Created by axel on 29/11/16.
 */
@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private Boolean returned;
    @ManyToOne(cascade = CascadeType.MERGE)
    @PrimaryKeyJoinColumn(name = "BORROWERID", referencedColumnName = "ID")
    private Borrower borrower;
    @ManyToOne(cascade = CascadeType.MERGE)
    @PrimaryKeyJoinColumn(name = "ITEMID", referencedColumnName = "ID")
    private Item item;


    /**
     * Instantiates a new Loan.
     *
     * @param start    the start
     * @param end      the end
     * @param returned the returned
     * @param borrower the borrower
     * @param item     the item
     */
    public Loan(ZonedDateTime start, ZonedDateTime end, Boolean returned, Borrower borrower, Item item) {
        this.start = start;
        this.end = end;
        this.returned = returned;
        this.borrower = borrower;
        this.item = item;
    }

    /**
     * Instantiates a new Loan.
     */
    public Loan() {
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
    public Borrower getBorrower() {
        return borrower;
    }

    /**
     * Sets borrower.
     *
     * @param borrower the borrower
     */
    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    /**
     * Gets item.
     *
     * @return the item
     */
    public Item getItem() {
        return item;
    }

    /**
     * Sets item.
     *
     * @param item the item
     */
    public void setItem(Item item) {
        this.item = item;
    }
}
