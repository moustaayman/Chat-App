package com.example.chatapp.controllers;

import com.example.chatapp.dao.implementation.UserdaoImpl;
import com.example.chatapp.models.User;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/UserCreationServlet")
public class UserCreationServlet extends HttpServlet {
    UserdaoImpl userdao;
    @Override
    public void init() throws ServletException {
        super.init();
        userdao = new UserdaoImpl();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String message="test";
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("new_email");
        String password = request.getParameter("new_password");
        //Crypter le mot de passe
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User();
        user.setUsername(username);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setPermission(User.PermissionType.NORMAL_USER);
        user.setStatus(User.Status.OFFLINE);
        user.setLastConnectionTime(null);
        userdao.saveUser(user);
        response.sendRedirect("userLogin.jsp?userID=" + user.getUSER_ID());
    }
}
