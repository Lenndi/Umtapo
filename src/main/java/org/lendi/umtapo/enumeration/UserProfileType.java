package org.lendi.umtapo.enumeration;

import java.io.Serializable;


/**
 * Created by axel on 29/11/16.
 */
public enum UserProfileType implements Serializable {

 USER("USER"),
 ADMIN("ADMIN");

 String userProfileType;

 private UserProfileType(String userProfileType){
  this.userProfileType = userProfileType;
 }

 public String getUserProfileType(){
  return userProfileType;
 }

}
