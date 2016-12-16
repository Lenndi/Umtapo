package org.lendi.umtapo.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.LocalDateTime;

/**
 * Subscription DTO.
 * <p>
 * Created by axel on 29/11/16.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SubscriptionDto {

    private Integer id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Integer contribution;
    private BorrowerDto borrower;
    private LibraryDto library;

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
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Sets start.
     *
     * @param start the start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Gets end.
     *
     * @return the end
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Sets end.
     *
     * @param end the end
     */
    public void setEnd(LocalDateTime end) {
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
     * Gets library.
     *
     * @return the library
     */
    public LibraryDto getLibrary() {
        return library;
    }

    /**
     * Sets library.
     *
     * @param library the library
     */
    public void setLibrary(LibraryDto library) {
        this.library = library;
    }
}
