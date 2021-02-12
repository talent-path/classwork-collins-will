package com.tp.backlogtracker.persistence;

import com.tp.backlogtracker.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class PartialUserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User partialUser = new User();
        partialUser.setUserID(resultSet.getInt("userID"));
        partialUser.setName(resultSet.getString("name"));
        return partialUser;
    }
}
