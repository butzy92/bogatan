package com.bogatan.rest;

import com.bogatan.exception.UnauthenticatedUserException;
import com.bogatan.model.UserContext;
import com.bogatan.repository.UsersRepository;
import com.bogatan.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mbutan on 4/10/2017.
 */
@RestController
@RequestMapping("api/users")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @RequestMapping("/self")
    public UserContext getSelf() throws UnauthenticatedUserException {
        return SecurityUtils.getAuthenticatedUserContext();
    }
}