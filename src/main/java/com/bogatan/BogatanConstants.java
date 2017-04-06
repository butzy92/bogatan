package com.bogatan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public final class BogatanConstants {

    @Value("${bogatan.secretKey}")
    private String secretKey;

    @Value("${bogatan.rolesKey}")
    private String rolesKey;

    @Value("${bogatan.authorizationHeader}")
    private String authorizationHeader;


    @Value("${facebook.clientId}")
    private String facebookClientId;

    @Value("${facebook.clientSecret}")
    private String facebookClientSecret;

    @Value("${facebook.callback}")
    private String facebookCallback;


    private BogatanConstants(){}

    public String getSecretKey() {
        return secretKey;
    }

    public String getRolesKey() {
        return rolesKey;
    }

    public String getAuthorizationHeader() {
        return authorizationHeader;
    }

    public String getFacebookClientId() {
        return facebookClientId;
    }

    public String getFacebookClientSecret() {
        return facebookClientSecret;
    }

    public String getFacebookCallback() {
        return facebookCallback;
    }
}
