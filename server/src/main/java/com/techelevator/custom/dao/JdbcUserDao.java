package com.techelevator.custom.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.Card;
import com.techelevator.custom.model.Item;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.techelevator.custom.model.User;

@Component
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserById(int userId) {

        User user = null;
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            if (results.next()) {
                user = mapRowToUser(results);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return user;
    }

    @Override
    public List<User> getUsers() {

        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY username";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                User user = mapRowToUser(results);
                users.add(user);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return users;
    }

    @Override
    public User getUserByUsername(String username) {

        if (username == null) {
            username = "";
        }
        User user = null;
        String sql = "SELECT * FROM users WHERE username = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
            if (results.next()) {
                user = mapRowToUser(results);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return user;
    }

    @Override
    public User createUser(User newUser) {

        User user = null;
        String insertUserSql = "INSERT INTO users " +
                "(username, password_hash, role, balance) " +
                "VALUES (?, ?, ?, ?) " +
                "RETURNING user_id";

        if (newUser.getHashedPassword() == null) {
            throw new DaoException("User cannot be created with null password");
        }
        try {
            String passwordHash = new BCryptPasswordEncoder().encode(newUser.getHashedPassword());

            Integer userId = jdbcTemplate.queryForObject(insertUserSql, int.class,
                    newUser.getUsername(), passwordHash, newUser.getRole(), new BigDecimal("0.00"));
            user =  getUserById(userId);
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return user;
    }

    @Override
    public User updateUser(User user) {
        User updatedUser = null;
        String sql = "UPDATE users SET username = ?, password_hash = ?, role = ?, balance = ? WHERE user_id = ?;";
        try {
            int numberOfRows = jdbcTemplate.update(sql, user.getUsername(), user.getHashedPassword(), user.getRole(), user.getBalance(), user.getId());
            if (numberOfRows == 0) {
                throw new DaoException("0 rows affected, expected at least 1");
            } else {
                updatedUser = getUserById(user.getId());
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return updatedUser;
    }

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setHashedPassword(rs.getString("password_hash"));
        user.setRole(rs.getString("role"));
        user.setBalance(rs.getBigDecimal("balance"));
        return user;
    }

}
