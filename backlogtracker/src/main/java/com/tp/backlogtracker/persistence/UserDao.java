package com.tp.backlogtracker.persistence;

import com.tp.backlogtracker.exceptions.InvalidUserIDException;
import com.tp.backlogtracker.models.User;

public interface UserDao {
    int addUser(Integer userID, String name);
    User getUserByID(Integer userID) throws InvalidUserIDException;
}