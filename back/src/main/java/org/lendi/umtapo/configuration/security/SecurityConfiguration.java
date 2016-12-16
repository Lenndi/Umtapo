package org.lendi.umtapo.configuration.security;

import org.lendi.umtapo.enumeration.UserProfileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * Spring security configuration class.
 * <p>
 * Created by axel on 29/11/16.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    /**
     * Instantiates a new Security configuration.
     *
     * @param userDetailsService the user details service
     */
    @Autowired
    public SecurityConfiguration(@Qualifier("customUserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Configure global security.
     *
     * @param auth the auth
     * @throws Exception the exception
     */
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /** {@inheritDoc} */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers("/consoleh2/**").hasRole(UserProfileType.ADMIN.getUserProfileType())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //.loginPage("/login")
                .usernameParameter("username").passwordParameter("password");

        http.headers().frameOptions().disable();

    }

    /**
     * Configure global.
     *
     * @param auth the auth
     * @throws Exception the exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles(UserProfileType.USER.getUserProfileType());
        auth
                .inMemoryAuthentication()
                .withUser("admin").password("password").roles(UserProfileType.ADMIN.getUserProfileType());
    }
}
