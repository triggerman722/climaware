package com.climaware.controller;

import com.climaware.model.User;
import com.climaware.security.Encryptor;
import com.climaware.service.UserService;

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
@WebServlet(value = "/logout/*")
public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.removeAttribute("username");
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
