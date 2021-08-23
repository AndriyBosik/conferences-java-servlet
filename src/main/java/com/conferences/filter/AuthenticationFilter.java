package com.conferences.filter;

import com.conferences.config.HttpMethods;
import com.conferences.config.Pages;
import com.conferences.filter.model.ServletData;
import com.conferences.filter.model.UrlData;
import com.conferences.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter extends UrlDividerFilter {

    @Override
    public void handleFilter(ServletData servletData, UrlData urlData, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletData.getServletRequest();
        User user = (User) request.getSession().getAttribute("user");
        String url = urlData.getPath();
        String httpMethod = request.getMethod();
        String userRole = "guest";
        if (user != null && user.getRole() != null) {
            userRole = user.getRole().getTitle();
        }

        if (canSkip(userRole, url, httpMethod)) {
            filterChain.doFilter(servletData.getServletRequest(), servletData.getServletResponse());
            return;
        }

        if (user == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/errors/401.jsp").forward(servletData.getServletRequest(), servletData.getServletResponse());
        }

        filterChain.doFilter(servletData.getServletRequest(), servletData.getServletResponse());
    }

    private boolean canSkip(String userRole, String url, String httpMethod) {
        return (Pages.HOME.toString().equals(url) && httpMethod.equals(HttpMethods.GET.toString())) ||
                (url.startsWith(Pages.HOME.toString()) && httpMethod.equals("POST")) ||
                (url.startsWith(Pages.PROFILE.toString()) && httpMethod.equals("GET") && userRole != "guest");
    }

}
