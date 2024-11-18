package com.dao;

import com.model.User;
import java.sql.*;

public class UserDAO {
    private Connection connection;

    // Constructor to initialize database connection
    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to register a new user
    public boolean registerUser(User user) throws SQLException {
        String query = "INSERT INTO Registration (Name, Email, DateOfBirth, Gender, PhoneNumber) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setDate(3, new java.sql.Date(user.getDateOfBirth().getTime()));
            statement.setString(4, user.getGender());
            statement.setString(5, user.getPhoneNumber());

            int result = statement.executeUpdate();
            return result > 0; // Returns true if the registration is successful
        }
    }
}
