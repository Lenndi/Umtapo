package org.lendi.umtapo.configuration.init;

import org.lendi.umtapo.dao.PrivilegeDao;
import org.lendi.umtapo.dao.RoleDao;
import org.lendi.umtapo.dao.UserDao;
import org.lendi.umtapo.entity.Privilege;
import org.lendi.umtapo.entity.Role;
import org.lendi.umtapo.entity.User;
import org.lendi.umtapo.enumeration.UserRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;


/**
 * Created by axel on 30/11/16.
 */
@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

 boolean alreadySetup = false;

 @Autowired
 private UserDao userDao;
 @Autowired
 private RoleDao roleDao;
 @Autowired
 private PrivilegeDao privilegeDao;
 @Autowired
 private PasswordEncoder passwordEncoder;

 @Bean
 public PasswordEncoder passwordEncoder() {
  return new BCryptPasswordEncoder();
 }

 @Override
 @Transactional
 public void onApplicationEvent(ContextRefreshedEvent event) {
  if (alreadySetup)
   return;
  Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
  Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
  List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
  createRoleIfNotFound(UserRoleEnum.ADMIN.getUserRoleEnum(), adminPrivileges);
  createRoleIfNotFound(UserRoleEnum.USER.getUserRoleEnum(), Arrays.asList(readPrivilege));

  Role adminRole = roleDao.findRoleByName(UserRoleEnum.ADMIN.getUserRoleEnum());
  User user = new User();
  user.setFirstName("Test");
  user.setLastName("Test");
  user.setPassword(passwordEncoder.encode("test"));
  user.setEmail("test@test.com");
  user.setRoles(Arrays.asList(adminRole));
  user.setEnabled(true);
  userDao.save(user);

  alreadySetup = true;
 }

 @Transactional
 private Privilege createPrivilegeIfNotFound(String name) {
  Privilege privilege = privilegeDao.findPrivilegeByName(name);
  if (privilege == null) {
   privilege = new Privilege(name);
   privilegeDao.save(privilege);
  }
  return privilege;
 }

 @Transactional
 private Role createRoleIfNotFound(String name, List<Privilege> privileges) {
  Role role = roleDao.findRoleByName(name);
  if (role == null) {
   role = new Role(name);
   role.setPrivileges(privileges);
   roleDao.save(role);
  }
  return role;
 }
}
