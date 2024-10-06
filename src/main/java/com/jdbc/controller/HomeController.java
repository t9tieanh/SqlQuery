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
        String command = request.getParameter("command");
        String userId = request.getParameter("userId");
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String resultMessage = "";

        try {
            if("insert".equalsIgnoreCase(command)) {
                System.out.println("hello");
                String sql = "INSERT INTO User(Email, FirstName, LastName) VALUES (?, ?, ?)";
                int index = abstractDAO.insert(sql, email, firstName, lastName);
                resultMessage = "Insert successfull!";
            }
            else if("update".equalsIgnoreCase(command)) {
                if (userId != null && !userId.isEmpty()) {
                    // Truy vấn bản ghi hiện tại từ database
                    UserModel existingUser = userDAO.findById(Integer.parseInt(userId));

                    if (email == null || email.isEmpty()) {
                        email = existingUser.getEmail();
                    }
                    if (firstName == null || firstName.isEmpty()) {
                        firstName = existingUser.getFirstName();
                    }
                    if (lastName == null || lastName.isEmpty()) {
                        lastName = existingUser.getLastName();
                    }
                    String sql = "UPDATE User SET Email = ?, FirstName = ?, LastName = ? WHERE UserId = ?";
                    abstractDAO.update(sql, email, firstName, lastName, userId);
                    resultMessage = "Update successful!";
                }
            }
            else if ("select".equalsIgnoreCase(command)) {
                List<UserModel> users = userDAO.findAll();
                request.setAttribute("users", users);
                resultMessage = "Query successful!";
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
