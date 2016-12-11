package org.lendi.umtapo.enumeration;

public enum UserProfileType {
	USER("USER"),
	DBA("DBA"),
	ADMIN("ADMIN");
	
	String userProfileType;
	
	UserProfileType(String userProfileType){
		this.userProfileType = userProfileType;
	}
	
	public String getUserProfileType(){
		return userProfileType;
	}
	
}
