package com.squallsoft.api.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.squallsoft.api.service.MyUserDetailsService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
prePostEnabled = true,
securedEnabled = true,
jsr250Enabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String[] AUTH_LIST = {
	        //"/myapp/version",
	        //"/myapp/history",
	        "/myapp/**",
	        "/mapper/**",
	        "/pedido/**",
	        "/pages/home",
	        "/js",
	        "/css",
	        "/icon"
	    };
	
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
        http.
             authorizeRequests()   
            .antMatchers(AUTH_LIST).permitAll()
            .antMatchers("/").hasAnyAuthority("CLIENTE", "ADMIN", "BURN")
            .anyRequest().authenticated()
            .and().csrf().disable()
            .formLogin().permitAll()
            .loginPage("/login").failureUrl("/login?error=true")
            .defaultSuccessUrl("/pages/home", true)
            .usernameParameter("user_name")
            .passwordParameter("password")
            .and().logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/pages/home").and().exceptionHandling()
            .accessDeniedPage("/access-denied");
        
        http.addFilterAfter(new CsrfTokenResponseHeaderBindingFilter(), CsrfFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("WEB-INF/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/icon/**");
    }
}

