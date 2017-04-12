package com.bogatan.rest;

import com.bogatan.BogatanConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;

@Controller
@RequestMapping("/facebook")
public class FacebookController {
    private final Logger log = LoggerFactory.getLogger(FacebookController.class);

    private static String REDIRECT_URL_PATTERN = "{0}://{1}:{2}/{3}";

    @Autowired
    private FacebookConnectionFactory facebookConnectionFactory;

    @Autowired
    private BogatanConstants bogatanConstants;

    @GetMapping("/login")
    @ResponseBody
    @CrossOrigin("*")
    public ResponseEntity helloFacebook(HttpServletRequest request) throws URISyntaxException {
        OAuth2Operations oauthOperations = facebookConnectionFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();
        params.setRedirectUri(getRedirectUri(request));
        String authorizeUrl = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, params);
        return ResponseEntity.status(HttpStatus.FOUND).location(new URI(authorizeUrl)).build();
    }

    @GetMapping("/callback")
    @ResponseBody
    @CrossOrigin("*")
    public String helloFacebook(@RequestParam("code") String code, HttpServletRequest request) {
        AccessGrant accessGrant = facebookConnectionFactory.getOAuthOperations().exchangeForAccess(code, getRedirectUri(request), null);
        /*just for the moment*/

        return "{\"token\": \""+accessGrant.getAccessToken()+"\"}";
    }


    private String getRedirectUri(HttpServletRequest request) {
        return MessageFormat.format(REDIRECT_URL_PATTERN,
                request.getScheme(),
                request.getServerName(),
                String.valueOf(request.getServerPort()),
                bogatanConstants.getFacebookCallback());
    }


}