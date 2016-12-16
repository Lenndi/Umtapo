package org.lendi.umtapo.enumeration;

/**
 * The enum User profile type.
 */
public enum UserProfileType {
    /**
     * User user profile type.
     */
    USER("USER"),
    /**
     * Dba user profile type.
     */
    DBA("DBA"),
    /**
     * Admin user profile type.
     */
    ADMIN("ADMIN");

    /**
     * The User profile type.
     */
    private String userProfileType;

    UserProfileType(String userProfileType) {
        this.userProfileType = userProfileType;
    }

    /**
     * Get user profile type string.
     *
     * @return the string
     */
    public String getUserProfileType() {
        return userProfileType;
    }

    /**
     * Sets user profile type.
     *
     * @param userProfileType the user profile type
     */
    public void setUserProfileType(String userProfileType) {
        this.userProfileType = userProfileType;
    }
}
