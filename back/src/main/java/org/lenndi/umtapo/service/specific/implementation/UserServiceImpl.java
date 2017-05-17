package org.lenndi.umtapo.service.specific.implementation;

import org.lenndi.umtapo.dao.UserDao;
import org.lenndi.umtapo.entity.User;
import org.lenndi.umtapo.service.generic.AbstractGenericService;
import org.lenndi.umtapo.service.specific.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User servuce implementation.
 * <p>
 * Created by axel on 29/11/16.
 */
@Service
public class UserServiceImpl extends AbstractGenericService<User, Integer> implements UserService {

    private final UserDao userDao;

    /**
     * Instantiates a new User service.
     *
     * @param userDao the user dao
     */
    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findBySso(String sso) {
        return userDao.findBySsoId(sso);
    }
}
