package org.lendi.umtapo.entity;

import org.lendi.umtapo.enumeration.Condition;

import javax.persistence.*;


/**
 * Created by axel on 29/11/16.
 */
@Entity
public class Item {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Integer id;
 private String type;
 private String internalId;
 private Integer purchasePrice;
 private Boolean loanable;
 @OneToOne
 private Loan loan;
 @Enumerated(EnumType.STRING)
 private Condition condition;
 @OneToOne
 private ExternalSource externalSource;

 public Integer getId() {
  return id;
 }


 public void setId(Integer id) {
  this.id = id;
 }


 public ExternalSource getExternalSource() {
  return externalSource;
 }


 public void setExternalSource(ExternalSource externalSource) {
  this.externalSource = externalSource;
 }


 public Condition getCondition() {
  return condition;
 }


 public void setCondition(Condition condition) {
  this.condition = condition;
 }


 public Loan getLoan() {
  return loan;
 }


 public void setLoan(Loan loan) {
  this.loan = loan;
 }


 public String getType() {
  return type;
 }


 public void setType(String type) {
  this.type = type;
 }


 public String getInternalId() {
  return internalId;
 }


 public void setInternalId(String internalId) {
  this.internalId = internalId;
 }


 public Integer getPurchasePrice() {
  return purchasePrice;
 }


 public void setPurchasePrice(Integer purchasePrice) {
  this.purchasePrice = purchasePrice;
 }


 public Boolean getLoanable() {
  return loanable;
 }


 public void setLoanable(Boolean loanable) {
  this.loanable = loanable;
 }
}
