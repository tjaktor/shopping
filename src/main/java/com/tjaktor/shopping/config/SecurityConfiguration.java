package com.tjaktor.shopping.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
		
		http.headers().frameOptions().disable();
		
		http.authorizeRequests().antMatchers("/konsoli/**", "/css/**", "/webjars/**", "/js/**", "/public/**", "/api/**").permitAll();
		
		// http.authorizeRequests().antMatchers("/").permitAll();
		
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/carts/**").hasRole("USER")
			.antMatchers("/admin/**").hasAnyRole("ADMIN")
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.loginPage("/login")
			.permitAll()
			.and()
		.logout()
			.invalidateHttpSession(true)
			.logoutUrl("/logout")
			.logoutSuccessUrl("/login?logout")
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.permitAll();
	}
	
	@Autowired
	public void configureAuth(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}	
}
