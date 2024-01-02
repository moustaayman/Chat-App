package com.example.chatapp.controllers;

import com.example.chatapp.dao.Messagedao;
import com.example.chatapp.dao.implementation.MessagedaoImpl;
import com.example.chatapp.models.Message;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ChatServlet", value = "/ChatServlet")
public class ChatServlet extends HttpServlet {
    Messagedao messagedao;
    Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        messagedao = new MessagedaoImpl();
        gson = new Gson();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Message> allMessages = messagedao.findAllMessages();

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String jsonResponse = new Gson().toJson(allMessages);

            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error processing JSON response", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
