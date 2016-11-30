package org.lendi.umtapo.entity;

import javax.persistence.*;
import java.util.List;


/**
 * Created by axel on 29/11/16.
 */
@Entity
@Table
public class Role {


 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private Integer id;
 private String name;
 @ManyToMany(mappedBy = "roles")
 private List<User> users;
 @ManyToMany
 @JoinTable(
     name = "roles_privileges",
     joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
     inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
 private List<Privilege> privileges;


 public Role(String name) {
  this.name = name;
 }


 public Role() {
 }


 public Integer getId() {
  return id;
 }


 public void setId(Integer id) {
  this.id = id;
 }


 public List<User> getUsers() {
  return users;
 }


 public void setUsers(List<User> users) {
  this.users = users;
 }


 public List<Privilege> getPrivileges() {
  return privileges;
 }


 public void setPrivileges(List<Privilege> privileges) {
  this.privileges = privileges;
 }


 public String getName() {
  return name;
 }


 public void setName(String name) {
  this.name = name;
 }
}
