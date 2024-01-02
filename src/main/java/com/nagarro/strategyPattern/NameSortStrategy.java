package com.nagarro.strategyPattern;

import com.nagarro.entities.UserTable;
import com.nagarro.repositories.UserTableRepo;

import java.util.List;


public class NameSortStrategy implements SortStrategy {

    UserTableRepo userTableRepo;

    
    public NameSortStrategy(UserTableRepo userTableRepo) {
        this.userTableRepo = userTableRepo;
    }

    @Override
    public List<UserTable> sort(String sortOrder, int limit, int offset) {
        if (sortOrder.equalsIgnoreCase("odd")) {
            return userTableRepo.findAllNamesWithOddCharacterCount(limit, offset);
        } else {
            return userTableRepo.findAllNamesWithEvenCharacterCount(limit, offset);
        }
    }
}
