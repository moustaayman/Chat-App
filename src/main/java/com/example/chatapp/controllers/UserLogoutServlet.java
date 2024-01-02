package com.example.chatapp.controllers;

import com.example.chatapp.dao.Messagedao;
import com.example.chatapp.dao.implementation.LogdaoImpl;
import com.example.chatapp.dao.implementation.MessagedaoImpl;
import com.example.chatapp.dao.implementation.UserdaoImpl;
import com.example.chatapp.models.Log;
import com.example.chatapp.models.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@WebServlet(name = "UserLogoutServlet", value = "/UserLogoutServlet")
public class UserLogoutServlet extends HttpServlet {
    UserdaoImpl userdao;
    LogdaoImpl logdao;

    @Override
    public void init() throws ServletException {
        super.init();
        userdao = new UserdaoImpl();
        logdao = new LogdaoImpl();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie cookie = new Cookie("jwtToken", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        HttpSession session = request.getSession();
        int loggedInUserId = (int) session.getAttribute("loggedInUserId");
        User loggedInUser = userdao.findUserById(loggedInUserId);
        loggedInUser.setStatus(User.Status.OFFLINE);
        loggedInUser.setLastConnectionTime(Timestamp.valueOf(LocalDateTime.now()));
        userdao.updateUser(loggedInUser);

        Log log = new Log();
        log.setUser(loggedInUser);
        log.setType(Log.LogType.DISCONNECTION);
        log.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        logdao.updateLog(log);

        request.getSession().invalidate();

        response.sendRedirect("userLogin.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
