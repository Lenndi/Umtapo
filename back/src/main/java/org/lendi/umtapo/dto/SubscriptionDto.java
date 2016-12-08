package org.lendi.umtapo.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;
import org.joda.time.DateTime;
import org.lendi.umtapo.configuration.CustomDateTimeSerializer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * Created by axel on 29/11/16.
 */
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SubscriptionDto {

 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private Integer id;
 @JsonSerialize(using = CustomDateTimeSerializer.class)
 private DateTime start;
 @JsonSerialize(using = CustomDateTimeSerializer.class)
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
