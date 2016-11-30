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
public class OpeningHours {

 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private Integer id;
 private Integer day;
 private Boolean isEnable;
 private Calendar morningOpening;
 private Calendar morningClosing;
 private Calendar afternoonOpening;
 private Calendar afternoonClosing;


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


 public Calendar getMorningOpening() {
  return morningOpening;
 }


 public void setMorningOpening(Calendar morningOpening) {
  this.morningOpening = morningOpening;
 }


 public Calendar getMorningClosing() {
  return morningClosing;
 }


 public void setMorningClosing(Calendar morningClosing) {
  this.morningClosing = morningClosing;
 }


 public Calendar getAfternoonOpening() {
  return afternoonOpening;
 }


 public void setAfternoonOpening(Calendar afternoonOpening) {
  this.afternoonOpening = afternoonOpening;
 }


 public Calendar getAfternoonClosing() {
  return afternoonClosing;
 }


 public void setAfternoonClosing(Calendar afternoonClosing) {
  this.afternoonClosing = afternoonClosing;
 }
}
