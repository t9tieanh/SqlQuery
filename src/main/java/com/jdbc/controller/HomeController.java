package com.jdbc.controller;


import com.jdbc.dao.AbstractDAO;
import com.jdbc.dao.UserDAO;
import com.jdbc.mapper.UserMapper;
import com.jdbc.model.UserModel;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

@WebServlet(urlPatterns = "/home")
public class HomeController extends HttpServlet {
    private AbstractDAO abstractDAO = new AbstractDAO();
    private UserDAO userDAO = new UserDAO();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("views/home.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String resultMessage = "";

        try {
            String command = request.getParameter("command");

            if ("select".equalsIgnoreCase(command)) {
                List<UserModel> users = userDAO.findAll();
                request.setAttribute("users", users);
                resultMessage = "Query successful!";

            } else {
                String email = request.getParameter("email");
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                UserModel user = new UserModel(email,firstName,lastName);

                if ("insert".equalsIgnoreCase(command)) {
                    if (userDAO.findByEmail(user.getEmail()) == null) {
                        int id = userDAO.insert(user);
                        resultMessage = MessageFormat.format("Insert {0} successfull!",id);
                    } else resultMessage = "user already exists !";
                }
                else if("update".equalsIgnoreCase(command)) {
                    int userId = Integer.valueOf(request.getParameter("userId"));
                    user.setId(userId);

                    UserModel existingUser = userDAO.findById(userId);
                    if (existingUser != null) {
                        userDAO.update(user);
                        resultMessage = "Update successful!";
                    } else resultMessage = "user does not exist yet!";
                }
            }
        }
        catch (Exception e) {
            resultMessage = "Error executing SQL: " + e.getMessage();
            e.printStackTrace();
        }
        request.setAttribute("resultMessage", resultMessage);
        doGet(request, response);
    }

}
