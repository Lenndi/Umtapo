package org.lenndi.umtapo.configuration.init;

import org.lenndi.umtapo.dao.UserDao;
import org.lenndi.umtapo.dao.UserProfileDao;
import org.lenndi.umtapo.entity.User;
import org.lenndi.umtapo.entity.UserProfile;
import org.lenndi.umtapo.enumeration.UserProfileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

/**
 * InitialDataloader entity.
 * <p>
 * Created by axel on 30/11/16.
 */
@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * The Already setup.
     */
    private boolean alreadySetup = false;

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserProfileDao userProfileDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Password encoder password encoder.
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        Set<UserProfile> userProfileSet = new HashSet<>();
        userProfileSet.add(createRoleIfNotFound(UserProfileType.ADMIN.getUserProfileType()));
        userProfileSet.add(createRoleIfNotFound(UserProfileType.USER.getUserProfileType()));

        UserProfile adminRole = userProfileDao.findByType(UserProfileType.ADMIN.getUserProfileType());
        User user = new User();
        user.setId(1);
        user.setFirstName("Test");
        user.setSsoId("Test");
        user.setLastName("Test");
        user.setPassword(passwordEncoder.encode("test"));
        user.setEmail("test@test.com");
        user.setUserProfiles(userProfileSet);
        userDao.save(user);

        alreadySetup = true;
    }

    @Transactional
    private UserProfile createRoleIfNotFound(String name) {
        UserProfile userProfile = userProfileDao.findByType(name);
        if (userProfile == null) {
            userProfile = new UserProfile(name);
            userProfileDao.save(userProfile);
        }
        return userProfile;
    }

    /**
     * Is already setup boolean.
     *
     * @return the boolean
     */
    public boolean isAlreadySetup() {
        return alreadySetup;
    }

    /**
     * Sets already setup.
     *
     * @param alreadySetup the already setup
     */
    public void setAlreadySetup(boolean alreadySetup) {
        this.alreadySetup = alreadySetup;
    }

    /**
     * Gets user dao.
     *
     * @return the user dao
     */
    public UserDao getUserDao() {
        return userDao;
    }

    /**
     * Sets user dao.
     *
     * @param userDao the user dao
     */
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Gets user profile dao.
     *
     * @return the user profile dao
     */
    public UserProfileDao getUserProfileDao() {
        return userProfileDao;
    }

    /**
     * Sets user profile dao.
     *
     * @param userProfileDao the user profile dao
     */
    public void setUserProfileDao(UserProfileDao userProfileDao) {
        this.userProfileDao = userProfileDao;
    }

    /**
     * Gets password encoder.
     *
     * @return the password encoder
     */
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    /**
     * Sets password encoder.
     *
     * @param passwordEncoder the password encoder
     */
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
