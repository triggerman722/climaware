package com.climaware.security.controller;

import com.climaware.security.model.User;
import com.climaware.security.service.Encryptor;
import com.climaware.security.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by greg on 3/3/19.
 */
@WebServlet(value = "/login/*")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/security/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserService userService = new UserService();
        User user = userService.getByUsername(username);

        if (user != null && Encryptor.validatePassword(password, user.getPassword())) {
            HttpSession session = req.getSession();
            session.setAttribute("username", username);
            resp.sendRedirect(req.getContextPath() + "/wind");
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/security/login.jsp").forward(req, resp);
        }

    }
}
