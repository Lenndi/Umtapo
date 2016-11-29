package org.lendi.umtapo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * Created by axel on 29/11/16.
 */
@Entity
public class Configuration {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Integer id;
 private String masterToken;


 public Integer getId() {
  return id;
 }


 public void setId(Integer id) {
  this.id = id;
 }


 public String getMasterToken() {
  return masterToken;
 }


 public void setMasterToken(String masterToken) {
  this.masterToken = masterToken;
 }
}
