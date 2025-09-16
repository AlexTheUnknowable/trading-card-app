package com.techelevator.custom.dao;

import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.Card;
import com.techelevator.custom.model.Type;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcTypeDao implements TypeDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTypeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Type getTypeById(int typeId) {
        Type type = null;
        String sql = "SELECT type_id, name FROM type WHERE type_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, typeId);
            if (results.next()) {
                type = mapRowToType(results);
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return type;
    }

    @Override
    public List<Type> getTypes() {
        List<Type> types = new ArrayList<>();
        String sql = "SELECT type_id, name FROM type;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Type type = mapRowToType(results);
                types.add(type);
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return types;
    }

    private Type mapRowToType(SqlRowSet results) {
        Type type = new Type();
        type.setId(results.getInt("type_id"));
        type.setName(results.getString("name"));
        return type;
    }
}
