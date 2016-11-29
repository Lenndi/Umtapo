package org.lendi.umtapo.entity;

import javax.persistence.*;


/**
 * Created by axel on 29/11/16.
 */
@Entity
public class User{

 @Id
 @GeneratedValue(strategy= GenerationType.IDENTITY)
 private Integer id;
 private String username;
 private String password;
 private String firstName;
 private String lastName;
 private String email;

 public Integer getId() {
  return id;
 }


 public void setId(Integer id) {
  this.id = id;
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


 public String getFirstName() {
  return firstName;
 }


 public void setFirstName(String firstName) {
  this.firstName = firstName;
 }


 public String getLastName() {
  return lastName;
 }


 public void setLastName(String lastName) {
  this.lastName = lastName;
 }


 public String getEmail() {
  return email;
 }


 public void setEmail(String email) {
  this.email = email;
 }
}
