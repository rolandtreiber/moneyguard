package com.moneyguard.moneyguard.filter;

import com.moneyguard.moneyguard.model.User;
import com.moneyguard.moneyguard.repository.UserRepository;
import com.moneyguard.moneyguard.security.jwt.services.UserDetailsImpl;
import com.moneyguard.moneyguard.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import java.io.IOException;

@Component
public class InjectAuthenticatedUserFilter implements Filter {

    @Autowired
    UserRepository userRepository;

    @Resource(name = "authService")
    AuthService authService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (userRepository.findByEmail(userDetails.getEmail()).isPresent()) {
                User user = userRepository.findByEmail(userDetails.getEmail()).get();
                authService.setAuthUser(user);
            } else {
                authService.setAuthUser(null);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

}
