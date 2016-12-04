package org.lendi.umtapo.dao;

import org.lendi.umtapo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by axel on 29/11/16.
 */
public interface UserDao extends JpaRepository<User, Integer> {

 User findBySsoId(String ssoId);
}
