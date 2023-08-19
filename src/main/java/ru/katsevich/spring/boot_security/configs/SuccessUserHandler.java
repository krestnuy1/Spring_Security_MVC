package ru.katsevich.spring.boot_security.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.katsevich.spring.boot_security.entities.User;
import ru.katsevich.spring.boot_security.services.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {

    private UserServiceImpl userService;
    @Autowired
    public SuccessUserHandler(UserServiceImpl userService) {
        this.userService = userService;
    }

    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_ADMIN")){
            httpServletResponse.sendRedirect("/admin");
        } else if (roles.contains("ROLE_USER")) {
            httpServletResponse.sendRedirect("/user");
        } else {
            httpServletResponse.sendRedirect("/");
        }
    }
}