package org.lendi.umtapo.entity;

import javax.persistence.*;


/**
 * Created by axel on 29/11/16.
 */
@Entity
public class ExternalSource {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Integer id;
 private String name;
 @OneToOne
 private Address address;


 public Integer getId() {
  return id;
 }


 public void setId(Integer id) {
  this.id = id;
 }


 public Address getAddress() {
  return address;
 }


 public void setAddress(Address address) {
  this.address = address;
 }


 public String getName() {
  return name;
 }


 public void setName(String name) {
  this.name = name;
 }
}
