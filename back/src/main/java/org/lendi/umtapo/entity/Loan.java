package org.lendi.umtapo.entity;

import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;


/**
 * Created by axel on 29/11/16.
 */
@Entity
public class Loan {

 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private Integer id;
 private DateTime date;

 public Loan(DateTime date) {
  this.date = date;
 }

 public Loan() {
 }

 public Integer getId() {
  return id;
 }


 public void setId(Integer id) {
  this.id = id;
 }


 public DateTime getDate() {
  return date;
 }

 public void setDate(DateTime date) {
  this.date = date;
 }
}
