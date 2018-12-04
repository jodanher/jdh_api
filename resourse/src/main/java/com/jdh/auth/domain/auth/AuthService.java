package com.jdh.auth.domain.auth;

import com.jdh.auth.domain.user.User;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    User login(String username, String password);

    String extractUserId(HttpServletRequest request);

    String verifyToken(String token);

    String generateToken(User user);

    User user(HttpServletRequest request);

    User validToken(String token);
}
