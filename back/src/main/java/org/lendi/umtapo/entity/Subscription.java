package org.lendi.umtapo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import java.time.ZonedDateTime;

/**
 * Subscription entity.
 * <p>
 * Created by axel on 29/11/16.
 */
@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private Integer contribution;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "BORROWER_ID")
    private Borrower borrower;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "LIBRARY_ID")
    private Library library;

    /**
     * Instantiates a new Subscription.
     */
    public Subscription() {
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
     * Gets contribution.
     *
     * @return the contribution
     */
    public Integer getContribution() {
        return contribution;
    }

    /**
     * Sets contribution.
     *
     * @param contribution the contribution
     */
    public void setContribution(Integer contribution) {
        this.contribution = contribution;
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
     * Gets library.
     *
     * @return the library
     */
    public Library getLibrary() {
        return library;
    }

    /**
     * Sets library.
     *
     * @param library the library
     */
    public void setLibrary(Library library) {
        this.library = library;
    }
}
