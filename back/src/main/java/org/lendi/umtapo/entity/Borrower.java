package org.lendi.umtapo.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Borrower entity.
 * <p>
 * Created by axel on 29/11/16.
 */
@Entity
public class Borrower {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String comment;
    private LocalDateTime birthday;
    private Integer quota;
    private Boolean emailOptin;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Subscription subscription;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Loan> loan;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Library library;

    public Borrower() {
    }

    public Borrower(String name, String comment, LocalDateTime birthday, Integer quota, Boolean emailOptin, Address address, Subscription subscription, List<Loan> loan, Library library) {
        this.name = name;
        this.comment = comment;
        this.birthday = birthday;
        this.quota = quota;
        this.emailOptin = emailOptin;
        this.address = address;
        this.subscription = subscription;
        this.loan = loan;
        this.library = library;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public List<Loan> getLoan() {
        return loan;
    }


    public void setLoan(List<Loan> loan) {
        this.loan = loan;
    }


    public Subscription getSubscription() {
        return subscription;
    }


    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }


    public Address getAddress() {
        return address;
    }


    public void setAddress(Address address) {
        this.address = address;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getComment() {
        return comment;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }


    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public Integer getQuota() {
        return quota;
    }


    public void setQuota(Integer quota) {
        this.quota = quota;
    }


    public Boolean getEmailOptin() {
        return emailOptin;
    }


    public void setEmailOptin(Boolean emailOptin) {
        this.emailOptin = emailOptin;
    }
}
