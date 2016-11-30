package org.lendi.umtapo.dao;

import org.lendi.umtapo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by axel on 29/11/16.
 */
public interface RoleDao extends JpaRepository<Role, Integer> {

 // Search a role by name
 Role findRoleByName(String name);
}
