package com.jdh.auth.infra.filter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomCorsFilter extends OncePerRequestFilter implements Filter {

    @Override
    public void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
            throws IOException, ServletException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD, PATCH");
        response.setHeader("Access-Control-Allow-Headers", getHeadersAllowed());
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "180");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(request, response);
        }

    }

    private String getHeadersAllowed() {
        StringBuilder headers = new StringBuilder();
        headers.append("Origin, ");
        headers.append("Content-Type, ");
        headers.append("Access-Control-Request-Headers, ");
        headers.append("Access-Control-Allow-Origin, ");
        headers.append("Accept, ");
        headers.append("Authorization, ");
        headers.append("WWW-Authenticate, ");
        headers.append("Location");
        headers.append("X-XSRF-TOKEN");

        return headers.toString();
    }
}
