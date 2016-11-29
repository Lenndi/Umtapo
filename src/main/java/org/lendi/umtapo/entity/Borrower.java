package org.lendi.umtapo.entity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;


/**
 * Created by axel on 29/11/16.
 */
@Entity
public class Borrower {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Integer id;
 private String name;
 private String comment;
 private Calendar birthday;
 private Integer quota;
 private Boolean emailOptin;
 @OneToOne
 private Address address;
 @OneToOne
 private Subscription subscription;
 @OneToMany
 private List<Loan> loan;


 public Integer getId() {
  return id;
 }

 public void setId(Integer id) {
  this.id = id;
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


 public Calendar getBirthday() {
  return birthday;
 }


 public void setBirthday(Calendar birthday) {
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
