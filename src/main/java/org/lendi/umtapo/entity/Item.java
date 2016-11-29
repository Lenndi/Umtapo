package org.lendi.umtapo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * Created by axel on 29/11/16.
 */
@Entity
public class Item {

 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private Integer id;
 private String type;
 private String internalId;
 private Integer purchasePrice;
 private Boolean loanable;


 public Integer getId() {
  return id;
 }


 public void setId(Integer id) {
  this.id = id;
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
