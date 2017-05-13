package org.lenndi.umtapo.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * Spring security configuration class.
 * <p>
 * Created by axel on 29/11/16.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Resource
    private Environment env;

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

    /**
     * Gets basic auth entry point.
     *
     * @return the basic auth entry point
     */
    @Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint() {
        return new CustomBasicAuthenticationEntryPoint();
    }

    /** {@inheritDoc} */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (env.getRequiredProperty("security.environment").equals("dev")) {
            http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll();
        } else {
            http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/consoleh2/**").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic().authenticationEntryPoint(getBasicAuthEntryPoint())
                .and()
                // We filter the api/login requests
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                // And filter other requests to check the presence of JWT in header
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        }
        http.headers().frameOptions().disable();
    }

//    /**
//     * Configure global.
//     *
//     * @param auth the auth
//     * @throws Exception the exception
//     */
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .inMemoryAuthentication()
//            .withUser("user").password("password").roles(UserProfileType.USER.getUserProfileType());
//        auth
//            .inMemoryAuthentication()
//            .withUser("aze").password("aze").roles(UserProfileType.ADMIN.getUserProfileType());
//    }
}
