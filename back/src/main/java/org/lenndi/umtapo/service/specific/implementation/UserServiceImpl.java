package org.lenndi.umtapo.service.specific.implementation;

import org.lenndi.umtapo.dao.UserDao;
import org.lenndi.umtapo.entity.User;
import org.lenndi.umtapo.service.generic.AbstractGenericService;
import org.lenndi.umtapo.service.specific.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public User save(User user) {

        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userDao.save(user);
    }

}
