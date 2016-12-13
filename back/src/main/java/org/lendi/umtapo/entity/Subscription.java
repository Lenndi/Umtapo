package org.lendi.umtapo.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Subscription entity.
 *
 * Created by axel on 29/11/16.
 */
@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Date start;
    private Date end;
    private Integer contribution;
    @ManyToOne
    @PrimaryKeyJoinColumn(name="BORROWERID", referencedColumnName="ID")
    private Borrower borrower;
    @ManyToOne
    @PrimaryKeyJoinColumn(name="LIBRARYID", referencedColumnName="ID")
    private Library library;

    public Subscription() {
    }

    public Subscription(Date start, Date end, Integer contribution) {
        this.start = start;
        this.end = end;
        this.contribution = contribution;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Integer getContribution() {
        return contribution;
    }

    public void setContribution(Integer contribution) {
        this.contribution = contribution;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}
