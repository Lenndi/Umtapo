package org.lendi.umtapo.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;
import org.joda.time.DateTime;
import org.lendi.umtapo.configuration.CustomDateTimeSerializer;

import java.util.List;


/**
 * Dto borrower object
 *
 * Created by axel on 29/11/16.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BorrowerDto {

 private Integer id;
 private String name;
 private String comment;
 @JsonSerialize(using = CustomDateTimeSerializer.class)
 private DateTime birthday;
 private Integer quota;
 private Boolean emailOptin;
 private AddressDto address;
 private SubscriptionDto subscription;
 private List<LoanDto> loan;
 private LibraryDto library;


 public Integer getId() {
  return id;
 }

 public void setId(Integer id) {
  this.id = id;
 }

 public DateTime getBirthday() {
  return birthday;
 }

 public void setBirthday(DateTime birthday) {
  this.birthday = birthday;
 }

 public LibraryDto getLibrary() {
  return library;
 }

 public void setLibrary(LibraryDto library) {
  this.library = library;
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
