package org.lendi.umtapo.service.specific.implementation;

import org.lendi.umtapo.dao.UserDao;
import org.lendi.umtapo.entity.User;
import org.lendi.umtapo.service.generic.AbstractGenericService;
import org.lendi.umtapo.service.specific.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
        Assert.notNull(userDao);
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
