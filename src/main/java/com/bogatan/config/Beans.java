package com.bogatan.config;

import com.bogatan.BogatanConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

import javax.jms.ConnectionFactory;

@Configuration
@EnableJms
public class Beans {

    @Autowired
    private BogatanConstants bogatanConstants;

    @Bean
    public FacebookConnectionFactory facebookConnectionFactory(){
        return new FacebookConnectionFactory(bogatanConstants.getFacebookClientId(), bogatanConstants.getFacebookClientSecret());
    }

    @Bean
    public JmsListenerContainerFactory<?> userFactory(ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }
}
