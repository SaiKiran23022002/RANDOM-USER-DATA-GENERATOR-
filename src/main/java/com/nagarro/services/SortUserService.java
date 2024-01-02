package com.nagarro.services;

import com.nagarro.entities.UserTable;

import java.util.List;

public interface SortUserService{

    List<UserTable> getSortedUsers(String sortType, String sortOrder, String limit, String offset);
}
