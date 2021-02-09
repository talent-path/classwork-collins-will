package com.tp.backlogtracker.persistence;

import com.tp.backlogtracker.exceptions.InvalidUserIDException;
import com.tp.backlogtracker.models.User;
import org.springframework.beans.factory.annotation.Autowired;
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
        User partialNewUser = template.queryForObject(
                "select \"userID\",\"name\"\n" +
                "from \"Users\"\n" +
                "where \"Users\".\"userID\" = ?;",
                new PartialUserMapper(),
                userID);
        if (partialNewUser.getUserID() == null && partialNewUser.getName() == null) {
            throw new InvalidUserIDException("No user with ID " + userID + " found");
        }

        return partialNewUser;
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
