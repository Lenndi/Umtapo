package org.lendi.umtapo.enumeration;

import java.io.Serializable;


/**
 * Created by axel on 29/11/16.
 */
public enum UserRoleEnum implements Serializable {

 USER("USER"),
 ADMIN("ADMIN");

 String userProfileType;

 private UserRoleEnum(String userProfileType){
  this.userProfileType = userProfileType;
 }

 public String getUserRoleEnum(){
  return userProfileType;
 }

}
