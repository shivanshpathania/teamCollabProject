// File: src/main/java/com/example/config/RoleBasedAuthenticationSuccessHandler.java
package com.example.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RoleBasedAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final SimpleUrlAuthenticationSuccessHandler studentSuccessHandler = new SimpleUrlAuthenticationSuccessHandler("/student/dashboard");
    private final SimpleUrlAuthenticationSuccessHandler mentorSuccessHandler = new SimpleUrlAuthenticationSuccessHandler("/mentor/dashboard");

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Check the user's role and delegate to the appropriate handler
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if ("ROLE_MENTOR".equals(auth.getAuthority())) {
                this.mentorSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                return;
            } else if ("ROLE_STUDENT".equals(auth.getAuthority())) {
                this.studentSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                return;
            }
        }
    }
}