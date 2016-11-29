package org.lendi.umtapo.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/**
 * Created by axel on 29/11/16.
 */
@Entity
public class UserRole {

 @ManyToOne
 @JoinColumn(name = "USER_ID")
 private User user;

 @ManyToOne
 @JoinColumn(name = "ROLE_ID")
 private Role role;


 public User getUser() {
  return user;
 }


 public void setUser(User user) {
  this.user = user;
 }


 public Role getRole() {
  return role;
 }


 public void setRole(Role role) {
  this.role = role;
 }
}
