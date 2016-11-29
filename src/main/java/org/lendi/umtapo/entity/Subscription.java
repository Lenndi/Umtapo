package org.lendi.umtapo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;


/**
 * Created by axel on 29/11/16.
 */
@Entity
public class Subscription {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Integer id;
 private Calendar start;
 private Calendar end;
 private Integer contribution;


 public Integer getId() {
  return id;
 }


 public void setId(Integer id) {
  this.id = id;
 }


 public Calendar getStart() {
  return start;
 }


 public void setStart(Calendar start) {
  this.start = start;
 }


 public Calendar getEnd() {
  return end;
 }


 public void setEnd(Calendar end) {
  this.end = end;
 }


 public Integer getContribution() {
  return contribution;
 }


 public void setContribution(Integer contribution) {
  this.contribution = contribution;
 }
}
