package org.lendi.umtapo.entity;

import java.util.List;

import javax.persistence.*;


/**
 * Created by axel on 29/11/16.
 */
@Entity
public class User{

 @Id
 @GeneratedValue(strategy= GenerationType.AUTO)
 private Integer id;
 private String username;
 private String password;
 private String firstName;
 private String lastName;
 private String email;
 private boolean enabled;
 @ManyToMany
 @JoinTable(
     name = "users_roles",
     joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
     inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
 private List<Role> roles;

 public Integer getId() {
  return id;
 }


 public void setId(Integer id) {
  this.id = id;
 }


 public List<Role> getRoles() {
  return roles;
 }


 public void setRoles(List<Role> roles) {
  this.roles = roles;
 }


 public boolean isEnabled() {
  return enabled;
 }


 public void setEnabled(boolean enabled) {
  this.enabled = enabled;
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
