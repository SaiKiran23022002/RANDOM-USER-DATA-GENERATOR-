package com.nagarro.strategyPattern;

import java.util.List ;

import com.nagarro.entities.UserTable;

public interface SortStrategy {
    List<UserTable> sort(String sortOrder, int limit, int offset);
}

