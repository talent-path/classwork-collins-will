package com.tp.backlogtracker.persistence;

import com.tp.backlogtracker.exceptions.InvalidUserIDException;
import com.tp.backlogtracker.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserPostgresDao implements UserDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public User getUserByID(int userID) throws InvalidUserIDException {
        User partialUser = null;

        try {
            partialUser = template.queryForObject(
                    "select \"userID\",\"name\"\n" +
                            "from \"Users\"\n" +
                            "where \"Users\".\"userID\" = ?;",
                    new PartialUserMapper(),
                    userID);
        } catch (EmptyResultDataAccessException ex) {
            throw new InvalidUserIDException("No user with ID " + userID + " found");
        }

        return partialUser;
    }

    class PartialUserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User partialUser = new User();
            partialUser.setUserID(resultSet.getInt("userID"));
            partialUser.setName(resultSet.getString("name"));
            return partialUser;
        }
    }
}
