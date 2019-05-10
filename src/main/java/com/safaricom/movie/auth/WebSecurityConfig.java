package com.safaricom.movie.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private ClientUserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JwtConfig jwtConfig;

	@Autowired
	private AuthenticationFilterBean authenticationFilterBean;


	// Spring has UserDetailsService interface, which can be overriden to provide our implementation for fetching user from database (or any other source).
	// The UserDetailsService object is used by the auth manager to load the user from database.
	// In addition, we need to define the password encoder also. So, auth manager can compare and verify passwords.
	@Bean
	public AuthenticationProvider authenticationProvider() {
		AuthenticationProvider authenticationProvider = new AuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(encoder);
		return authenticationProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint(){
		return new CustomAuthenticationEntryPoint();
	}

	   @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                // make sure we use stateless session; session won't be used to store user's state.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // handle an authorized attempts
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                .and()
                // What's the authenticationManager()?
                // An object provided by WebSecurityConfigurerAdapter, used to authenticate the user passing user's credentials
                // The filter needs this auth manager to authenticate the user.
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig))
                .addFilterBefore(authenticationFilterBean, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                // allow  POST requests for /auth
                .antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()
                // any other requests must be authenticated
                .antMatchers(HttpMethod.POST,"/api/v1/users").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/users/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/users").permitAll()
                .antMatchers(HttpMethod.PUT,"/api/v1/users").permitAll()
                .antMatchers(HttpMethod.DELETE,"/api/v1/users").permitAll()
                
                .antMatchers(HttpMethod.POST,"/api/v1/movies").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/movies").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/movies/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/api/v1/movies").permitAll()
                .antMatchers(HttpMethod.DELETE,"/api/v1/movies/**").permitAll()
                .anyRequest().authenticated();
    }
}
