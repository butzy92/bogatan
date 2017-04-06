package com.bogatan.config;

import com.bogatan.BogatanConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

@Configuration
public class Beans {

    @Autowired
    private BogatanConstants bogatanConstants;

    @Bean
    public FacebookConnectionFactory facebookConnectionFactory(){
        return new FacebookConnectionFactory(bogatanConstants.getFacebookClientId(), bogatanConstants.getFacebookClientSecret());
    }
}
