package com.bogatan.security.filter;

import com.bogatan.BogatanConstants;
import com.bogatan.exception.BogatanException;
import com.bogatan.exception.InvalidAccessTokenException;
import com.bogatan.exception.AccessTokenNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccessTokenFilter extends GenericFilterBean {
    private final Logger log = LoggerFactory.getLogger(AccessTokenFilter.class);

    private BogatanConstants bogatanConstants;
    private FacebookConnectionFactory connectionFactory;

    public AccessTokenFilter(BogatanConstants bogatanConstants, FacebookConnectionFactory connectionFactory) {
        this.bogatanConstants = bogatanConstants;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("doFilter for AccessTokenFilter");
        try {
            Optional<String> optionalJwt = getJWTValue(servletRequest);
            if(optionalJwt.isPresent()){
                verifyFacebookApi(optionalJwt.get(),servletRequest);
            }else{
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
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(new String[]{"ROLES_USER"})
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            User principal = new User(connection.getDisplayName(), "", authorities);
            UsernamePasswordAuthenticationToken us = new UsernamePasswordAuthenticationToken(principal, "", authorities);
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
