package org.lendi.umtapo.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.ZonedDateTime;
import java.util.List;


/**
 * Dto borrower object
 * <p>
 * Created by axel on 29/11/16.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BorrowerDto {

 private Integer id;
 private String name;
 private String comment;
 private ZonedDateTime birthday;
 private Integer quota;
 private Boolean emailOptin;
 private AddressDto address;
 private List<SubscriptionDto> subscription;
 private List<LoanDto> loan;
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
  * Gets birthday.
  *
  * @return the birthday
  */
 public ZonedDateTime getBirthday() {
  return birthday;
 }

 /**
  * Sets birthday.
  *
  * @param birthday the birthday
  */
 public void setBirthday(ZonedDateTime birthday) {
  this.birthday = birthday;
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

 /**
  * Gets address.
  *
  * @return the address
  */
 public AddressDto getAddress() {
  return address;
 }

 /**
  * Sets address.
  *
  * @param address the address
  */
 public void setAddress(AddressDto address) {
  this.address = address;
 }

 /**
  * Gets subscription.
  *
  * @return the subscription
  */
 public List<SubscriptionDto> getSubscription() {
  return subscription;
 }

 /**
  * Sets subscription.
  *
  * @param subscription the subscription
  */
 public void setSubscription(List<SubscriptionDto> subscription) {
  this.subscription = subscription;
 }

 /**
  * Gets loan.
  *
  * @return the loan
  */
 public List<LoanDto> getLoan() {
  return loan;
 }

 /**
  * Sets loan.
  *
  * @param loan the loan
  */
 public void setLoan(List<LoanDto> loan) {
  this.loan = loan;
 }

 /**
  * Gets name.
  *
  * @return the name
  */
 public String getName() {
  return name;
 }

 /**
  * Sets name.
  *
  * @param name the name
  */
 public void setName(String name) {
  this.name = name;
 }

 /**
  * Gets comment.
  *
  * @return the comment
  */
 public String getComment() {
  return comment;
 }

 /**
  * Sets comment.
  *
  * @param comment the comment
  */
 public void setComment(String comment) {
  this.comment = comment;
 }

 /**
  * Gets quota.
  *
  * @return the quota
  */
 public Integer getQuota() {
  return quota;
 }

 /**
  * Sets quota.
  *
  * @param quota the quota
  */
 public void setQuota(Integer quota) {
  this.quota = quota;
 }

 /**
  * Gets email optin.
  *
  * @return the email optin
  */
 public Boolean getEmailOptin() {
  return emailOptin;
 }

 /**
  * Sets email optin.
  *
  * @param emailOptin the email optin
  */
 public void setEmailOptin(Boolean emailOptin) {
  this.emailOptin = emailOptin;
 }
}
