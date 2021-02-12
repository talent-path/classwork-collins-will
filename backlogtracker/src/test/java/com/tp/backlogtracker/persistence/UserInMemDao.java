package com.tp.backlogtracker.persistence;

import com.tp.backlogtracker.exceptions.InvalidUserIDException;
import com.tp.backlogtracker.exceptions.NoGamesFoundException;
import com.tp.backlogtracker.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("serviceTesting")
public class UserInMemDao implements UserDao {

    @Autowired
    JdbcTemplate template;

    List<User> allUsers = new ArrayList<>();

    public UserInMemDao() {
        allUsers.clear();
    }

    @Override
    public int addUser(Integer userID, String name) {
        User newUser = new User();
        newUser.setUserID(userID);
        newUser.setName(name);
        allUsers.add(newUser);
        return userID;
    }

    @Override
    public User getUserByID(Integer userID) throws InvalidUserIDException {
        if (userID == null) {
            throw new InvalidUserIDException("User ID cannot be null");
        }

        User partialUser = null;

        for (User toCheck : allUsers) {
            if (toCheck.getUserID() == userID) {
                partialUser = toCheck;
                break;
            }
        }

        if (partialUser == null) {
            throw new InvalidUserIDException("No user found with ID " + userID);
        }

        return partialUser;
    }

    @Override
    public double getUserAveragePlayTime(Integer userID) throws NoGamesFoundException, InvalidUserIDException {
        return 0;
    }
}
