package org.lendi.umtapo.entity;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Created by axel on 29/11/16.
 */
@Entity
@Table
public class Role {

 private String name;
 private String title;


 public String getName() {
  return name;
 }


 public void setName(String name) {
  this.name = name;
 }


 public String getTitle() {
  return title;
 }


 public void setTitle(String title) {
  this.title = title;
 }
}
