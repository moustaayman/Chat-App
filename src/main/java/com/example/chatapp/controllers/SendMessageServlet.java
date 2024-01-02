package com.example.chatapp.controllers;

import com.example.chatapp.dao.Messagedao;
import com.example.chatapp.dao.implementation.LogdaoImpl;
import com.example.chatapp.dao.implementation.MessagedaoImpl;
import com.example.chatapp.dao.implementation.UserdaoImpl;
import com.example.chatapp.models.Log;
import com.example.chatapp.models.Message;
import com.example.chatapp.models.User;
import com.google.gson.JsonObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "SendMessageServlet", value = "/SendMessageServlet")
public class SendMessageServlet extends HttpServlet {
    Messagedao messagedao;
    UserdaoImpl userdao;
    LogdaoImpl logdao;

    @Override
    public void init() throws ServletException {
        super.init();
        messagedao = new MessagedaoImpl();
        userdao = new UserdaoImpl();
        logdao = new LogdaoImpl();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int loggedInUserId = (int) session.getAttribute("loggedInUserId");
        User loggedInUser = userdao.findUserById(loggedInUserId);
        System.out.println("/////////////////////////////////////////////");
        System.out.println("User status value: " + loggedInUser.getStatus().getValue());
        if(loggedInUser.getStatus().getValue()==2){
            System.out.println("Before redirection to BannedPage.jsp");
            response.sendRedirect(request.getContextPath() + "/BannedPage.jsp");
            System.out.println("After redirection to BannedPage.jsp");
            return;
        }

        String newMessageContent = request.getParameter("newMessage");

        Message newMessage = new Message();
        newMessage.setContent(newMessageContent);

        newMessage.setUser(loggedInUser);
        newMessage.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        messagedao.saveMessage(newMessage);

        Log log = new Log();
        log.setUser(loggedInUser);
        log.setType(Log.LogType.MESSAGE_SENT);
        log.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        logdao.saveLog(log);

        loggedInUser.setStatus(User.Status.ONLINE);
        System.out.println(loggedInUser.getStatus());

        response.sendRedirect("chat.jsp?userId=" + loggedInUser.getUSER_ID());
    }
}
