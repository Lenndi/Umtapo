package org.lendi.umtapo.dto;

import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * Created by axel on 29/11/16.
 */
@Entity
public class SubscriptionDto {

 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private Integer id;
 private DateTime start;
 private DateTime end;
 private Integer contribution;


 public Integer getId() {
  return id;
 }


 public void setId(Integer id) {
  this.id = id;
 }


 public DateTime getStart() {
  return start;
 }


 public void setStart(DateTime start) {
  this.start = start;
 }


 public DateTime getEnd() {
  return end;
 }


 public void setEnd(DateTime end) {
  this.end = end;
 }


 public Integer getContribution() {
  return contribution;
 }


 public void setContribution(Integer contribution) {
  this.contribution = contribution;
 }
}
