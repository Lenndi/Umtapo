package org.lendi.umtapo.entity;

import org.lendi.umtapo.enumeration.UserProfileType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The type User profile.
 */
@Entity
@Table(name = "USER_PROFILE")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private final Integer length = 15;

    @Column(name = "TYPE", length = length, unique = true, nullable = false)
    private String type = UserProfileType.USER.getUserProfileType();


    /**
     * Instantiates a new User profile.
     */
    public UserProfile() {
    }


    /**
     * Instantiates a new User profile.
     *
     * @param type the type
     */
    public UserProfile(String type) {
        this.type = type;
    }


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
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result + id) * prime;
        if (type != null) {
            result += type.hashCode();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof UserProfile)) {
            return false;
        }
        UserProfile other = (UserProfile) obj;
        if (!id.equals(other.id)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "UserProfile [id=" + id + ",  type=" + type + "]";
    }

}
