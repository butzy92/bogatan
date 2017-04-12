package com.bogatan.receivers;

import com.bogatan.model.UserContext;
import com.bogatan.persistance.Users;
import com.bogatan.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component
public class UserReceiver {
    @Autowired
    private UsersService usersService;

    @JmsListener(destination = "userVerify", containerFactory = "userFactory")
    public void receiveUserMessage(UserContext message) {
        usersService.createUserIfNotExists(message);
    }

}