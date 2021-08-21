package com.conferences.filter;

import com.conferences.config.HttpMethods;
import com.conferences.config.Pages;
import com.conferences.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter extends DispatcherFilter {

    @Override
    public void processApp(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if (canSkip(request)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/errors/401.jsp").forward(servletRequest, servletResponse);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean canSkip(HttpServletRequest request) {
        String path = request.getRequestURI();
        User user = (User) request.getSession().getAttribute("user");
        String userRole = "guest";
        if (user != null && user.getRole() != null) {
            userRole = user.getRole().getTitle();
        }

        return (Pages.HOME.toString().equals(path) && request.getMethod().equals(HttpMethods.GET.toString())) ||
                (path.startsWith(Pages.HOME.toString()) && request.getMethod().equals("POST")) ||
                (path.startsWith(Pages.PROFILE.toString()) && request.getMethod().equals("GET") && userRole != "guest");
    }

}
