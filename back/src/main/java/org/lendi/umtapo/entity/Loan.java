package org.lendi.umtapo.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Loan entity.
 *
 * Created by axel on 29/11/16.
 */
@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Date date;
    @ManyToOne
    @PrimaryKeyJoinColumn(name="BORROWERID", referencedColumnName="ID")
    private Borrower borrower;
    @ManyToOne
    @PrimaryKeyJoinColumn(name="ITEMID", referencedColumnName="ID")
    private Item item;

    public Loan(Date date) {
        this.date = date;
    }

    public Loan() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
