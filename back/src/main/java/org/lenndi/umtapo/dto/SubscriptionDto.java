package org.lenndi.umtapo.dto;

import java.time.ZonedDateTime;

/**
 * Subscription DTO.
 * <p>
 * Created by axel on 29/11/16.
 */
public class SubscriptionDto {

    private Integer id;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private Float contribution;
    private SimpleBorrowerDto borrower;
    private SimpleLibraryDto library;

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
    public Float getContribution() {
        return contribution;
    }

    /**
     * Sets contribution.
     *
     * @param contribution the contribution
     */
    public void setContribution(Float contribution) {
        this.contribution = contribution;
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
     * Gets library.
     *
     * @return the library
     */
    public SimpleLibraryDto getLibrary() {
        return library;
    }

    /**
     * Sets library.
     *
     * @param library the library
     */
    public void setLibrary(SimpleLibraryDto library) {
        this.library = library;
    }
}
