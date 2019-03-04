package com.climaware.controller;

import com.climaware.model.User;
import com.climaware.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by greg on 3/3/19.
 */
@WebServlet(value = "/users/*")
public class UserController extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("userid");

        List<User> users = new ArrayList<>();

        if (id == null) {
            users = userService.getAll();
        } else {
            User user = userService.get(Long.valueOf(id));
            users.add(user);
        }

        req.setAttribute("users", users);
        getServletContext().getRequestDispatcher("/awardlist.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String passwordconfirm = req.getParameter("passwordConfirm");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPasswordConfirm(passwordconfirm);
        user.setId(null);

        userService.add(user);

        resp.sendRedirect(req.getContextPath() + "/members?username=" + username);
    }

}
