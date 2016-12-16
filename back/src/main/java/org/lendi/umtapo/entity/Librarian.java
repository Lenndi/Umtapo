package org.lendi.umtapo.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Librarian entity.
 * <p>
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

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
  return id;
 }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
  this.id = id;
 }

    /**
     * Gets address.
     *
     * @return the address
     */
    public Address getAddress() {
  return address;
 }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(Address address) {
  this.address = address;
 }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
  return name;
 }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
  this.name = name;
 }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
  return username;
 }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
  this.username = username;
 }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
  return password;
 }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
  this.password = password;
 }
}
