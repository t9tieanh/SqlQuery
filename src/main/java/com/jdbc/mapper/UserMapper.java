package com.jdbc.mapper;

import com.jdbc.model.UserModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<UserModel>{

    @Override
    public UserModel mapRow(ResultSet resultSet) {
        try {
            UserModel user = new UserModel();
            user.setId(resultSet.getInt("UserID"));
            user.setEmail(resultSet.getString("Email"));
            user.setFirstName(resultSet.getString("FirstName"));
            user.setLastName(resultSet.getString("LastName"));
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
