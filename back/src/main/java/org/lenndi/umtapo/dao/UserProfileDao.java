package org.lenndi.umtapo.dao;

import org.lenndi.umtapo.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserProfile DAO.
 * <p>
 * Created by axel on 29/11/16.
 */
public interface UserProfileDao extends JpaRepository<UserProfile, Integer> {

    /**
     * Find by type user profile.
     *
     * @param type the type
     * @return the user profile
     */
// Search a role by name
    UserProfile findByType(String type);
}
