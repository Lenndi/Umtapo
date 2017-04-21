package org.lenndi.umtapo.configuration.security;

import org.lenndi.umtapo.entity.User;
import org.lenndi.umtapo.entity.UserProfile;
import org.lenndi.umtapo.service.specific.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Custom user details service.
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    /**
     * Instantiates a new Custom user details service.
     *
     * @param userService the user service
     */
    @Autowired
    public CustomUserDetailsService(UserService userService) {
        Assert.notNull(userService);
        this.userService = userService;
    }


    /** {@inheritDoc} */
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String ssoId)
            throws UsernameNotFoundException {
        User user = userService.findBySso(ssoId);
        System.out.println("User : " + user);
        if (user == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("Username not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getSsoId(), user.getPassword(),
                user.getState().equals("Active"), true, true, true, getGrantedAuthorities(user));
    }


    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (final UserProfile userProfile : user.getUserProfiles()) {
            System.out.println("UserProfile : " + userProfile);
            authorities.add(new SimpleGrantedAuthority("ROLE_" + userProfile.getType()));
        }
        System.out.print("authorities :" + authorities);
        return authorities;
    }

}
