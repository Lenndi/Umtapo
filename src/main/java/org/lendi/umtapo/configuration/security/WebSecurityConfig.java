package org.lendi.umtapo.configuration.security;

import org.lendi.umtapo.enumeration.UserRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * Spring security configuration class.
 *
 * Created by axel on 28/11/16.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

 @Override
 protected void configure(HttpSecurity http) throws Exception {
  http
	  .csrf().disable()
	  .authorizeRequests()
	  .antMatchers("/**").permitAll()
	  .antMatchers("/consoleh2/**").hasRole(UserRoleEnum.ADMIN.getUserRoleEnum())
	  .anyRequest().authenticated()
	  .and()
	  .formLogin()
	  //	  .loginPage("/login")
	  .permitAll()
	  .and()
	  .logout()
	  .permitAll();

  // add this line to use H2 web console
  http.headers().frameOptions().disable();
 }

 @Autowired
 public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
  auth
	  .inMemoryAuthentication()
	  .withUser("user").password("password").roles(UserRoleEnum.USER.getUserRoleEnum());
  auth
	  .inMemoryAuthentication()
	  .withUser("admin").password("password").roles(UserRoleEnum.ADMIN.getUserRoleEnum());
 }
}
