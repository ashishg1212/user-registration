package com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.UserDAO;
import com.model.User;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private static final String SUCCESS_PAGE = "/success.jsp"; // Page to redirect to on successful registration
    private static final String ERROR_PAGE = "/error.jsp";     // Page to redirect on error

    private Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            // Load MySQL JDBC Driver dynamically
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the database connection
            String dbURL = "jdbc:mysql://localhost:3306/your_db_name";
            String dbUser = "your_username";
            String dbPassword = "your_password";
            connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
        } catch (ClassNotFoundException e) {
            throw new ServletException("MySQL Driver not found.", e);
        } catch (SQLException e) {
            throw new ServletException("Database connection problem.", e);
        }
    }
    // Handle POST request to register user
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String dob = request.getParameter("dob");
        String gender = request.getParameter("gender");
        String phone = request.getParameter("phone");

        if (name == null || email == null || dob == null || gender == null || phone == null) {
            request.setAttribute("errorMessage", "All fields are required!");
            RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_PAGE);
            dispatcher.forward(request, response);
            return;
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            user.setDateOfBirth(sdf.parse(dob)); // Parse the date of birth
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Invalid date format.");
            RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_PAGE);
            dispatcher.forward(request, response);
            return;
        }
        user.setGender(gender);
        user.setPhoneNumber(phone);

        // Register user via DAO
        UserDAO userDAO = new UserDAO(connection);
        try {
            boolean isRegistered = userDAO.registerUser(user);

            if (isRegistered) {
                response.sendRedirect(request.getContextPath() + SUCCESS_PAGE);
            } else {
                request.setAttribute("errorMessage", "Registration failed. Try again.");
                RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_PAGE);
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error during registration.", e);
        }
    }

    // Close database connection
    @Override
    public void destroy() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
