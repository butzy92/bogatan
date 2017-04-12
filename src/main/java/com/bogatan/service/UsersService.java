package com.bogatan.service;

import com.bogatan.exception.UnimplementedApiException;
import com.bogatan.model.UserContext;
import com.bogatan.persistance.Users;
import org.springframework.social.connect.Connection;

public interface UsersService {
    UserContext getUserContextFromConnection(Connection connection) throws UnimplementedApiException;
    Users createUserIfNotExists(UserContext userContext);
}