package com.authbridge.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.authbridge.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MyLoginHandler myLoginHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		http.authorizeRequests()
			.antMatchers("/search").authenticated()
			 .antMatchers("/searchQuery").authenticated()
			  .antMatchers("/dashboard").access("hasRole('ROLE_ADMIN')")
			   .antMatchers("/abbr").access("hasRole('ROLE_ADMIN')")
			    .antMatchers("/stopword").access("hasRole('ROLE_ADMIN')")
			     .antMatchers("/alias").access("hasRole('ROLE_ADMIN')")
			      .antMatchers("/weightage").access("hasRole('ROLE_ADMIN')")
			       .antMatchers("/schedular","/saveScheduler" , "/addeditschedular").access("hasRole('ROLE_ADMIN')")
					.and()
					 	.formLogin().loginPage("/login")
							.usernameParameter("username").passwordParameter("password")
								.successHandler(myLoginHandler)  
								  .and()
									.logout().logoutSuccessUrl("/login?logout") 
										.and()
											.exceptionHandling().accessDeniedPage("/403")
												.and()
													.csrf().and().headers().defaultsDisabled().cacheControl();
	
	
		http.authorizeRequests()
		.antMatchers("/rest/**").permitAll()
				.and().csrf().disable();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
		auth.userDetailsService(userService);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/fonts/**", "/image/**", "/js/**");
	}
}