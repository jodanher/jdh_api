package com.jdh.auth.infra.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component
public class OAuth2Filter extends OAuth2AuthenticationProcessingFilter {

    public OAuth2Filter(AuthenticationManager authenticationManager, TokenStore tokenStore) {
        setAuthenticationManager(authenticationManager);
        setStateless(true);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (req.getAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_VALUE) == null) {
            chain.doFilter(req, res);
        } else {
            super.doFilter(req, res, chain);
        }
    }
}
