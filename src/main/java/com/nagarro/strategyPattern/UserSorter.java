package com.nagarro.strategyPattern;

import java.util.List;

import com.nagarro.entities.UserTable;

public class UserSorter {

    private SortStrategy sortStrategy;

    public UserSorter(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public void setSortStrategy(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public List<UserTable> sortUsers(String sortOrder, int limit, int offset) {
        return sortStrategy.sort(sortOrder, limit, offset);
    }
}
