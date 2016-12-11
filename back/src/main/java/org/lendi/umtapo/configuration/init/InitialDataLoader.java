package org.lendi.umtapo.configuration.init;

import org.lendi.umtapo.dao.UserDao;
import org.lendi.umtapo.dao.UserProfileDao;
import org.lendi.umtapo.entity.User;
import org.lendi.umtapo.entity.UserProfile;
import org.lendi.umtapo.enumeration.UserProfileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

/**
 * InitialDataloader entity.
 *
 * Created by axel on 30/11/16.
 */
@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    private final UserDao userDao;
    private final UserProfileDao userProfileDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitialDataLoader(UserDao userDao, UserProfileDao userProfileDao, PasswordEncoder passwordEncoder) {
        Assert.notNull(userDao);
        Assert.notNull(userProfileDao);
        Assert.notNull(passwordEncoder);
        this.userDao = userDao;
        this.userProfileDao = userProfileDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;
        Set<UserProfile> userProfileSet = new HashSet<>();
        userProfileSet.add(createRoleIfNotFound(UserProfileType.ADMIN.getUserProfileType()));
        userProfileSet.add(createRoleIfNotFound(UserProfileType.USER.getUserProfileType()));

        UserProfile adminRole = userProfileDao.findByType(UserProfileType.ADMIN.getUserProfileType());
        User user = new User();
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
}
