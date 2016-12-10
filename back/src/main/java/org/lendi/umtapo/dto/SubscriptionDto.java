package org.lendi.umtapo.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


/**
 * Created by axel on 29/11/16.
 */
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SubscriptionDto {

 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private Integer id;
 private LocalDateTime start;
 private LocalDateTime end;
 private Integer contribution;


 public Integer getId() {
  return id;
 }


 public void setId(Integer id) {
  this.id = id;
 }


 public LocalDateTime getStart() {
  return start;
 }


 public void setStart(LocalDateTime start) {
  this.start = start;
 }


 public LocalDateTime getEnd() {
  return end;
 }


 public void setEnd(LocalDateTime end) {
  this.end = end;
 }


 public Integer getContribution() {
  return contribution;
 }


 public void setContribution(Integer contribution) {
  this.contribution = contribution;
 }
}
