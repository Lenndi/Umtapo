package org.lendi.umtapo.dao;

import org.lendi.umtapo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User DAO.
 * <p>
 * Created by axel on 29/11/16.
 */
public interface UserDao extends JpaRepository<User, Integer> {

    /**
     * Find by sso id user.
     *
     * @param ssoId the sso id
     * @return the user
     */
    User findBySsoId(String ssoId);
}
