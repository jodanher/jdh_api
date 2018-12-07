package com.jdh.auth.domain.auth;

import com.jdh.auth.domain.user.User;
import com.jdh.auth.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${security.oauth2.clientId}")
    private String clientId;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AuthorizationServerTokenServices services;

    @Autowired
    private JwtAccessTokenConverter converter;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Override
    public User login(String username, String password) {
        Authentication authenticate =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList()));
        return userService.findById(UUID.fromString(authenticate.getName())).orElseThrow(() -> new AccessDeniedException("Usuário e/ou senha inválidos"));

    }

    @Override
    public String extractUserId(HttpServletRequest request) {
        TokenExtractor tokenExtractor = new BearerTokenExtractor();
        Authentication authentication = tokenExtractor.extract(request);
        //        OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(authentication.getName());
        return verifyToken(authentication.getName());
    }

    @Override
    public String verifyToken(String token) {
        OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(token);
        return oAuth2Authentication.getName();
    }

    @Override
    public String generateToken(User user) {
        OAuth2Request storedRequest = createOAuth2Request(clientId, user.getEmail(), user.getPassword(), null, Collections.EMPTY_LIST);
        Authentication usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(user.getId().toString(), user.getPassword(), storedRequest.getAuthorities());
        OAuth2AccessToken token = services.createAccessToken(new OAuth2Authentication(storedRequest, usernamePasswordAuthenticationToken));
        return converter.enhance(token, new OAuth2Authentication(storedRequest, usernamePasswordAuthenticationToken)).getValue();
    }

    @Override
    public User user(HttpServletRequest request) {
        return userService.findById(UUID.fromString(extractUserId(request))).orElse(null);
    }

    @Override
    public User validToken(String token) {
        String userId = verifyToken(token);
        return userService.findById(UUID.fromString(userId)).orElse(null);
    }

    public static OAuth2Request createOAuth2Request(String clientId, String username, String password, Set<String> scope,
                                                    Collection<? extends GrantedAuthority> authorities) {
        if (scope == null) {
            scope = new HashSet<>(1);
            scope.add("openid");
        }
        Map<String, String> requestParameters = new HashMap<>(3);
        requestParameters.put("grant_type", "password");
        requestParameters.put("username", username);
        requestParameters.put("password", password);
        requestParameters.put("scope", scope.iterator().next());
        return new OAuth2Request(requestParameters, clientId, authorities, true, scope, Collections.EMPTY_SET, null, Collections.EMPTY_SET,
                Collections.EMPTY_MAP);
    }

}
