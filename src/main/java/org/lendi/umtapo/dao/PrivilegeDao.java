package org.lendi.umtapo.dao;

import org.lendi.umtapo.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by axel on 29/11/16.
 */
public interface PrivilegeDao extends JpaRepository<Privilege, Integer> {

 // Search a privilege by name
 Privilege findPrivilegeByName(String name);
}
