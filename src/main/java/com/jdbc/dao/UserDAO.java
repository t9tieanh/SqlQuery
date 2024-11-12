package com.jdbc.dao;

import com.jdbc.mapper.UserMapper;
import com.jdbc.model.UserModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO<UserModel>{
    public List<UserModel> findAll() {
        String sql = "SELECT * FROM user_phamtiennanh";
        return query(sql, new UserMapper());
    }

    public UserModel findById(int id) {
        String sql = "SELECT * FROM user_phamtiennanh WHERE ID = ?";
        List<UserModel> users = query(sql, new UserMapper(), id);  // Truy vấn với userId
        return users.isEmpty() ? null : users.get(0);
    }

    public UserModel findByEmail (String email) {
        String sql = "SELECT * FROM user_phamtiennanh WHERE email = ?";
        List<UserModel> users = query(sql, new UserMapper(), email);  // Truy vấn với userId
        return users.isEmpty() ? null : users.get(0);
    }

    public void update (UserModel user) {
        String sql = "UPDATE user_phamtiennanh SET Email = ?, FirstName = ?, LastName = ? WHERE Id = ?";
        super.update(sql, user.getEmail(), user.getFirstName(), user.getLastName(), user.getId());
    }

    public int insert (UserModel user) {
        String sql = "INSERT INTO user_phamtiennanh(Email, FirstName, LastName) VALUES (?, ?, ?)";
        return super.insert(sql, user.getEmail(), user.getFirstName(), user.getLastName());
    }
}
