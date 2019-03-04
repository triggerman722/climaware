package com.climaware.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by greg on 3/3/19.
 */
@WebFilter(urlPatterns = { "/users/*","/awards/*", "/members/*"})
public class AuthenticationFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
        HttpSession session = httpReq.getSession();
        String username = (String)session.getAttribute("username");
        if (username!=null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.sendRedirect(httpReq.getContextPath() + "/login");
        }
    }

    @Override
    public void destroy() {

    }
}
