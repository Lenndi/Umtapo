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


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService;
	
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	 http
		 .csrf().disable()
		 .authorizeRequests()
//		 .antMatchers("/**").permitAll() Disable security
		 .antMatchers("/consoleh2/**").hasRole(UserProfileType.ADMIN.getUserProfileType())
		 .anyRequest().authenticated()
		 .and()
		 .formLogin()
//		 .loginPage("/login")
		 .permitAll()
		 .and()
		 .logout()
		 .permitAll();
	}

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
