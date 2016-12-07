package org.lendi.umtapo.entity;

import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * Created by axel on 29/11/16.
 */
@Entity
public class OpeningHours {

 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private Integer id;
 private Integer day;
 private Boolean isEnable;
 private DateTime morningOpening;
 private DateTime morningClosing;
 private DateTime afternoonOpening;
 private DateTime afternoonClosing;


 public Integer getId() {
  return id;
 }

 public void setId(Integer id) {
  this.id = id;
 }

 public Integer getDay() {
  return day;
 }

 public void setDay(Integer day) {
  this.day = day;
 }

 public Boolean getEnable() {
  return isEnable;
 }

 public void setEnable(Boolean enable) {
  isEnable = enable;
 }

 public DateTime getMorningOpening() {
  return morningOpening;
 }

 public void setMorningOpening(DateTime morningOpening) {
  this.morningOpening = morningOpening;
 }

 public DateTime getMorningClosing() {
  return morningClosing;
 }

 public void setMorningClosing(DateTime morningClosing) {
  this.morningClosing = morningClosing;
 }

 public DateTime getAfternoonOpening() {
  return afternoonOpening;
 }

 public void setAfternoonOpening(DateTime afternoonOpening) {
  this.afternoonOpening = afternoonOpening;
 }

 public DateTime getAfternoonClosing() {
  return afternoonClosing;
 }

 public void setAfternoonClosing(DateTime afternoonClosing) {
  this.afternoonClosing = afternoonClosing;
 }
}
