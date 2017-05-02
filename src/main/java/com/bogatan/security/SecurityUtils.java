package com.bogatan.security;

import com.bogatan.exception.UnauthenticatedUserException;
import com.bogatan.model.UserContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by mbutan on 4/12/2017.
 */
public final class SecurityUtils {
    private SecurityUtils(){}

    public static UserContext getAuthenticatedUserContext() throws UnauthenticatedUserException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            throw new UnauthenticatedUserException();
        }
        UserContext uc = (UserContext) authentication.getPrincipal();
        if(uc == null){
            throw new UnauthenticatedUserException();
        }
        return uc;
    }
}
