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
    private ZonedDateTime date;

    @ManyToOne(cascade = CascadeType.MERGE)
    @PrimaryKeyJoinColumn(name = "BORROWERID", referencedColumnName = "ID")
    private Borrower borrower;
    @ManyToOne(cascade = CascadeType.MERGE)
    @PrimaryKeyJoinColumn(name = "ITEMID", referencedColumnName = "ID")
    private Item item;

    /**
     * Instantiates a new Loan.
     *
     * @param date     the date
     * @param borrower the borrower
     * @param item     the item
     */
    public Loan(ZonedDateTime date, Borrower borrower, Item item) {
        this.date = date;
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
     * Gets date.
     *
     * @return the date
     */
    public ZonedDateTime getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(ZonedDateTime date) {
        this.date = date;
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
