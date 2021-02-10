package com.tp.backlogtracker.persistence;

import com.tp.backlogtracker.exceptions.InvalidUserIDException;
import com.tp.backlogtracker.models.User;

public interface UserDao {
    User getUserByID(Integer userID) throws InvalidUserIDException;
}
