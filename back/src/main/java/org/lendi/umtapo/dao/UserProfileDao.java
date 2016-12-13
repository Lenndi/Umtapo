package org.lendi.umtapo.dao;

import org.lendi.umtapo.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserProfile DAO.
 *
 * Created by axel on 29/11/16.
 */
public interface UserProfileDao extends JpaRepository<UserProfile, Integer> {

    // Search a role by name
    UserProfile findByType(String type);
}
