package org.lenndi.umtapo.service.specific.implementation;

import org.lenndi.umtapo.dao.UserDao;
import org.lenndi.umtapo.entity.User;
import org.lenndi.umtapo.exception.SsoIdEqualsPasswordException;
import org.lenndi.umtapo.service.specific.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * User servuce implementation.
 * <p>
 * Created by axel on 29/11/16.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    /**
     * Instantiates a new User service.
     *
     * @param userDao         the user dao
     * @param passwordEncoder the password encoder
     */
    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
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
    public User save(User user) throws SsoIdEqualsPasswordException {

        if (user.getSsoId().equals(user.getPassword())) {
            throw new SsoIdEqualsPasswordException("SsoId And Password are equal");
        }

        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userDao.save(user);
    }

    @Override
    public User findOne(Integer id) {

        return this.userDao.findOne(id);
    }

}
