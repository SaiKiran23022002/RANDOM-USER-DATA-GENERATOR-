package com.nagarro.strategyPattern;

import com.nagarro.entities.UserTable;
import com.nagarro.repositories.UserTableRepo;

import java.util.List;


public class AgeSortStrategy implements SortStrategy {

    UserTableRepo userTableRepo;

    public AgeSortStrategy(UserTableRepo userTableRepo) {
        this.userTableRepo = userTableRepo;
    }

    @Override
    public List<UserTable> sort(String sortOrder, int limit, int offset) {
        if (sortOrder.equalsIgnoreCase("even")) {
            return userTableRepo.findAllEvenAgesWithLimitAndOffset(limit, offset);
        } else {
            return userTableRepo.findAllOddAgesWithLimitAndOffset(limit, offset);
        }
    }
}

