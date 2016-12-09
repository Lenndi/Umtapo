package org.lendi.umtapo.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;
import org.lendi.umtapo.configuration.CustomDateTimeSerializer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;


/**
 * Loan dto.
 *
 * Created by axel on 29/11/16.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class LoanDto {

 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private Integer id;
 @JsonSerialize(using = CustomDateTimeSerializer.class)
 private DateTime date;


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
