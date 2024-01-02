package com.nagarro.services;

import java.util.List;  
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.entities.UserTable;
import com.nagarro.repositories.UserTableRepo;
import com.nagarro.singleton.Validator;
import com.nagarro.singleton.ValidatorFactory;
import com.nagarro.strategyPattern.AgeSortStrategy;
import com.nagarro.strategyPattern.NameSortStrategy;
import com.nagarro.strategyPattern.UserSorter;

@Service
public class SortUserServiceImpl implements SortUserService{

	UserTableRepo userTableRepo;

	UserTableServiceImpl userTableService;

	ValidatorFactory validatorFactory = new ValidatorFactory();
	
	@Autowired
	public SortUserServiceImpl(UserTableRepo userTableRepo,UserTableServiceImpl userTableService) {
		this.userTableRepo= userTableRepo;
		this.userTableService=userTableService;
		
	}

	public List<UserTable> getSortedUsers(String sortType, String sortOrder, String limit, String offset) {
		
		Validator sortTypeValidator = validatorFactory.createValidator(sortType);
		Validator sortOrderValidator = validatorFactory.createValidator(sortOrder);
		Validator limitValidator = validatorFactory.createValidator(limit);
		Validator offsetValidator = validatorFactory.createValidator(offset);
		
		if (!sortTypeValidator.validateInput(sortType) || !sortOrderValidator.validateInput(sortOrder)
				|| !limitValidator.validateInput(limit) || !offsetValidator.validateInput(offset)) {
	        throw new CustomErrorServiceImpl("Ensure sortType is either 'name' or 'age' and sortOrder is 'even' or 'odd', both case-insensitive. Also, ensure limit and offset are positive integers.");
		}
		if (Integer.parseInt(limit) > 5 || Integer.parseInt(limit) < 1 || !limitValidator.validateInput(limit)) {
	        throw new CustomErrorServiceImpl("Limit can be from 1 to 5 i.e. min=1 and max=5.");
		}
		
		UserSorter userSorter = new UserSorter(new AgeSortStrategy(userTableRepo));
		if (sortType.equalsIgnoreCase("name")) {
			userSorter.setSortStrategy(new NameSortStrategy(userTableRepo));
		}

		return userSorter.sortUsers(sortOrder, Integer.parseInt(limit), Integer.parseInt(offset))
                .stream()
                .collect(Collectors.toList());
	}
}
