package org.lendi.umtapo.service.specific.implementation;


import org.lendi.umtapo.dao.UserDao;
import org.lendi.umtapo.entity.User;
import org.lendi.umtapo.service.generic.implementation.GenericServiceImpl;
import org.lendi.umtapo.service.specific.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * User servuce implementation.
 *
 * Created by axel on 29/11/16.
 */
@Service
public class UserServiceImpl extends GenericServiceImpl<User, Integer> implements UserService {

 @Autowired
 private UserDao userDao;

 @Override public User findBySso(String sso) {
  return userDao.findBySsoId(sso);
 }
}
