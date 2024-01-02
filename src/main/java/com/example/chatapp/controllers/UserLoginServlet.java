package com.example.chatapp.controllers;

import com.example.chatapp.dao.Messagedao;
import com.example.chatapp.dao.implementation.LogdaoImpl;
import com.example.chatapp.dao.implementation.MessagedaoImpl;
import com.example.chatapp.dao.implementation.UserdaoImpl;
import com.example.chatapp.models.Log;
import com.example.chatapp.models.Message;
import com.example.chatapp.models.User;
import com.example.chatapp.util.JwtUtils;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "UserLoginServlet", value = "/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
    UserdaoImpl userdao;
    MessagedaoImpl messagedao;
    LogdaoImpl logdao;

    @Override
    public void init() throws ServletException {
        super.init();
        userdao = new UserdaoImpl();
        messagedao = new MessagedaoImpl();
        logdao = new LogdaoImpl();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userdao.findUserByEmail(email);

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            if(user.getStatus().getValue()==2){
                response.sendRedirect(request.getContextPath() + "/BannedPage.jsp");
                return;
            }
            User loggedInUser = userdao.findUserById(user.getUSER_ID());
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUserId", loggedInUser.getUSER_ID());

            //COOKIE
            String token = JwtUtils.generateToken(String.valueOf(loggedInUser.getUSER_ID()));
            Cookie userIdCookie = new Cookie("userIdCookie", token);
            userIdCookie.setHttpOnly(true);
            userIdCookie.setSecure(true);
            userIdCookie.setPath("/");
            response.addCookie(userIdCookie);

            Log log = new Log();
            log.setUser(loggedInUser);
            log.setType(Log.LogType.CONNECTION);
            log.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
            logdao.saveLog(log);

            loggedInUser.setStatus(User.Status.ONLINE);
            loggedInUser.setLastConnectionTime(Timestamp.valueOf(LocalDateTime.now()));
            userdao.updateUser(loggedInUser);

            List<Message> messages = messagedao.findAllMessages();
            request.setAttribute("messages", messages);
            response.sendRedirect("chat.jsp?userId=" + loggedInUser.getUSER_ID());
        } else {
            response.sendRedirect("login-error.jsp");
        }
    }
}
