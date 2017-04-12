package com.bogatan.service.impl;

import com.bogatan.exception.UnimplementedApiException;
import com.bogatan.persistance.Users;
import com.bogatan.repository.UsersRepository;
import com.bogatan.model.UserContext;
import com.bogatan.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public UserContext getUserContextFromConnection(Connection connection) throws UnimplementedApiException {
        Object api = connection.getApi();
        UserContext userContext = null;
        if (api instanceof Facebook) {
            Facebook fbApi = (Facebook) api;
            userContext = new UserContext()
                    .setId(fbApi.userOperations().getUserProfile().getId())
                    .setEmail(fbApi.userOperations().getUserProfile().getEmail())
                    .setName(fbApi.userOperations().getUserProfile().getName())
                    .setProfilePicture(connection.getImageUrl());
        }

        if (userContext == null) {
            throw new UnimplementedApiException();
        }

        jmsTemplate.convertAndSend("userVerify", userContext);
        return userContext;
    }

    @Override
    public Users createUserIfNotExists(UserContext userContext) {
        String userId = userContext.getId();
        Users user = usersRepository.findOne(userId);
        if (user == null) {
            user = new Users()
                    .setId(userId)
                    .setEmail(userContext.getEmail())
                    .setName(userContext.getName())
                    .setProfilePicture(userContext.getProfilePicture())
                    .setAddDate(new Date());
            usersRepository.save(user);
        } else {
            if (!userContext.isTheSameUser(user)) {
                user.setEmail(userContext.getEmail())
                        .setName(userContext.getName())
                        .setProfilePicture(userContext.getProfilePicture());
                usersRepository.save(user);
            }
        }
        return user;
    }
}
