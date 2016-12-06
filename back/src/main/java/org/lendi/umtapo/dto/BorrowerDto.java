package org.lendi.umtapo.dto;

import java.util.Calendar;
import java.util.List;


/**
 * Dto borrower object
 *
 * Created by axel on 29/11/16.
 */
public class BorrowerDto {

 private Integer id;
 private String name;
 private String comment;
 private Calendar birthday;
 private Integer quota;
 private Boolean emailOptin;
 private AddressDto address;
 private SubscriptionDto subscription;
 private List<LoanDto> loan;


 public Integer getId() {
  return id;
 }

 public void setId(Integer id) {
  this.id = id;
 }


 public AddressDto getAddress() {
  return address;
 }


 public void setAddress(AddressDto address) {
  this.address = address;
 }


 public SubscriptionDto getSubscription() {
  return subscription;
 }


 public void setSubscription(SubscriptionDto subscription) {
  this.subscription = subscription;
 }


 public List<LoanDto> getLoan() {
  return loan;
 }


 public void setLoan(List<LoanDto> loan) {
  this.loan = loan;
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
