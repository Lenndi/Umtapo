package org.lendi.umtapo.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.entity.Library;

import java.time.LocalDateTime;

/**
 * Subscription DTO.
 *
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Integer getContribution() {
        return contribution;
    }

    public void setContribution(Integer contribution) {
        this.contribution = contribution;
    }

    public BorrowerDto getBorrower() {
        return borrower;
    }

    public void setBorrower(BorrowerDto borrower) {
        this.borrower = borrower;
    }

    public LibraryDto getLibrary() {
        return library;
    }

    public void setLibrary(LibraryDto library) {
        this.library = library;
    }
}
