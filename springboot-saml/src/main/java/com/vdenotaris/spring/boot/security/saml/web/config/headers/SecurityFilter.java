package com.vdenotaris.spring.boot.security.saml.web.config.headers;

/**
 * Created by sudheerds on 21/12/17.
 */
import com.vdenotaris.spring.boot.security.saml.web.config.jwt.JWTUtil;
import com.vdenotaris.spring.boot.security.saml.web.stereotypes.CustomAuthUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecurityFilter implements javax.servlet.Filter {

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(req);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomAuthUser user = (CustomAuthUser) authentication.getPrincipal();
        //mutableRequest.putHeader("x-custom-header", "custom value");
        mutableRequest.putHeader("jwt", JWTUtil.buildJWTToken(user).serialize());
        chain.doFilter(mutableRequest, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
}