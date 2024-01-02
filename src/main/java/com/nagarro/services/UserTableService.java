package com.nagarro.services;

import com.nagarro.entities.UserTable;

public interface UserTableService {

    UserTable addUser(UserTable user);
    long getTotalCount();
}
