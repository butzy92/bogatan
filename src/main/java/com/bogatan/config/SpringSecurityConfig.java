package com.bogatan.config;

import com.bogatan.BogatanConstants;
import com.bogatan.security.filter.AccessTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BogatanConstants bogatanConstants;

    @Autowired
    private FacebookConnectionFactory facebookConnectionFactory;


    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and().addFilterBefore(new AccessTokenFilter(bogatanConstants, facebookConnectionFactory),
                UsernamePasswordAuthenticationFilter.class);
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/*.json")
                .antMatchers("/facebook/*")
                .antMatchers("/facebook")
                .antMatchers("/")
                .antMatchers("/index.html")
                .antMatchers("/img/*")
                .antMatchers("/templates/**/*.html");
    }
}
