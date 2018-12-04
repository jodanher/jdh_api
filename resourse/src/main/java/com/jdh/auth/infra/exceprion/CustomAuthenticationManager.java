package com.jdh.auth.infra.exceprion;

import com.jdh.auth.domain.auth.AuthService;
import com.jdh.auth.domain.user.User;
import com.jdh.auth.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenStore tokenStore;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String principal = (String) authentication.getPrincipal();
        String password = null;
        String token = null;

        Optional<User> optionalUser;
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            optionalUser = userService.findOneByEmail(principal);
            password = (String) authentication.getCredentials();
        } else {
            token = principal;
            OAuth2Authentication auth2Authentication = tokenStore.readAuthentication(principal);
            UUID id = UUID.fromString((String) auth2Authentication.getPrincipal());
            optionalUser = userService.findById(id);
        }

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getToken() == null) {
                new UnauthorizedUserException("Não autorizado");
            }
            if (password == null || passwordEncoder.matches(password, user.getPassword())) {
                if (token != null) {
                    user.setToken(token);
                }
                user.setLastLoginDate(LocalDate.now());
                userService.save(user);
                return new UsernamePasswordAuthenticationToken(user.getId().toString(), user.getPassword(),
                        authentication.getAuthorities());
            }
        }
        throw new AccessDeniedException("Usuário e/ou senha inválidos");
    }
}
