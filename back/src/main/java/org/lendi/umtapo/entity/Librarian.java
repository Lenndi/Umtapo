package org.lendi.umtapo.entity;

import javax.persistence.*;


/**
 * Librarian entity.
 *
 * Created by axel on 29/11/16.
 */
@Entity
public class Librarian {

 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private Integer id;
 private String name;
 private String username;
 private String password;
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

 public String getUsername() {
  return username;
 }

 public void setUsername(String username) {
  this.username = username;
 }

 public String getPassword() {
  return password;
 }

 public void setPassword(String password) {
  this.password = password;
 }
}
