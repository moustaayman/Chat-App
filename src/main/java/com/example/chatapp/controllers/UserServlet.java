package com.example.chatapp.controllers;

import com.example.chatapp.dao.Messagedao;
import com.example.chatapp.dao.Userdao;
import com.example.chatapp.dao.implementation.MessagedaoImpl;
import com.example.chatapp.dao.implementation.UserdaoImpl;
import com.example.chatapp.models.Message;
import com.example.chatapp.models.User;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UserServlet", value = "/UserServlet")
public class UserServlet extends HttpServlet {
    Userdao userdao;
    Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        userdao = new UserdaoImpl();
        gson = new Gson();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<User> allusers = userdao.findAllUsers();

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String jsonResponse = new Gson().toJson(allusers);

            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error processing JSON response", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            int loggedInUserId = (int) session.getAttribute("loggedInUserId");
            User loggedInUser = userdao.findUserById(loggedInUserId);
            String action = request.getParameter("action");

            if (loggedInUser.getPermission().getValue() == 2) {
                if ("ban".equals(action)) {
                    int userIdToBan = Integer.parseInt(request.getParameter("userId"));
                    User user = userdao.findUserById(userIdToBan);
                    user.setStatus(User.Status.BANNED);
                    user.setLastConnectionTime(Timestamp.valueOf(LocalDateTime.now()));
                    userdao.updateUser(user);
                }
            } else if (loggedInUser.getPermission().getValue() == 1) {
                if ("ban".equals(action)) {
                    int userIdToBan = Integer.parseInt(request.getParameter("userId"));
                    User user = userdao.findUserById(userIdToBan);
                    if(user.getPermission().getValue()==2 || user.getPermission().getValue()==1) return;
                    user.setStatus(User.Status.BANNED);
                    user.setLastConnectionTime(Timestamp.valueOf(LocalDateTime.now()));
                    userdao.updateUser(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
