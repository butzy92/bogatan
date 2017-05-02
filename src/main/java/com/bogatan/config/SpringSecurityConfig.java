package com.bogatan.config;

import com.bogatan.BogatanConstants;
import com.bogatan.repository.UsersRepository;
import com.bogatan.security.filter.AccessTokenFilter;
import com.bogatan.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BogatanConstants bogatanConstants;

    @Autowired
    private FacebookConnectionFactory facebookConnectionFactory;

    @Autowired
    private UsersService usersService;


    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and().addFilterBefore(this.corsFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new AccessTokenFilter(bogatanConstants, facebookConnectionFactory, usersService),
                        UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/h2-console/**")
                .antMatchers("/api/public/**");
    }
}
