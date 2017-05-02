package com.bogatan.security.filter;

import com.bogatan.BogatanConstants;
import com.bogatan.exception.BogatanException;
import com.bogatan.exception.InvalidAccessTokenException;
import com.bogatan.exception.AccessTokenNotFoundException;
import com.bogatan.model.UserContext;
import com.bogatan.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Permission;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AccessTokenFilter extends GenericFilterBean {
    private final Logger log = LoggerFactory.getLogger(AccessTokenFilter.class);

    private BogatanConstants bogatanConstants;
    private FacebookConnectionFactory connectionFactory;
    private UsersService usersService;

    public AccessTokenFilter(BogatanConstants bogatanConstants, FacebookConnectionFactory connectionFactory) {
        this.bogatanConstants = bogatanConstants;
        this.connectionFactory = connectionFactory;
    }

    public AccessTokenFilter(BogatanConstants bogatanConstants, FacebookConnectionFactory connectionFactory, UsersService usersService) {
        this.bogatanConstants = bogatanConstants;
        this.connectionFactory = connectionFactory;
        this.usersService = usersService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("doFilter for AccessTokenFilter");
        try {
            Optional<String> optionalJwt = getJWTValue(servletRequest);
            if (optionalJwt.isPresent()) {
                verifyFacebookApi(optionalJwt.get(), servletRequest);
            } else {
                throw new AccessTokenNotFoundException();
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (BogatanException e) {
            log.error(e.getMessage());
            log.trace("Trace: {}", e);
            ((HttpServletResponse) servletResponse).sendError(e.getStatus().value(), e.getMessage());
        }
    }


    private void verifyFacebookApi(String jwt, ServletRequest request) throws InvalidAccessTokenException {
        try {
            AccessGrant accessGrant = new AccessGrant(jwt);
            Connection<Facebook> connection = connectionFactory.createConnection(accessGrant);

            if (!connection.test()) {
                throw new InvalidAccessTokenException();
            } else {
                /*to be sure that we are in the same application
                * it will stay like this until we will integrate another social client
                * TO BE CACHED IN THE FUTURE THE ACCESS_TOKEN*/
                Map<String, String> appInfo = connection.getApi().restOperations().getForObject(connection.getApi().getBaseGraphApiUrl() + "/app?fields=id", Map.class);
                if(!bogatanConstants.getFacebookClientId().equals(appInfo.get("id"))){
                    throw new InvalidAccessTokenException();
                }
            }
            UserContext userContext = usersService.getUserContextFromConnection(connection);
            UsernamePasswordAuthenticationToken us = new UsernamePasswordAuthenticationToken(userContext, "", userContext.getAuthorities());
        /*http://stackoverflow.com/questions/4664893/how-to-manually-set-an-authenticated-user-in-spring-security-springmvc*/
            us.setDetails(new WebAuthenticationDetails((HttpServletRequest) request));

            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(us);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.trace("Trace: {}", e);
            throw new InvalidAccessTokenException();
        }
    }

    private Optional<String> getJWTValue(ServletRequest request) {
        return Optional.ofNullable(((HttpServletRequest) request)
                .getHeader(bogatanConstants.getAuthorizationHeader()));
    }
}
