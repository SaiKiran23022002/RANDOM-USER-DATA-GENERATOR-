package com.nagarro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.entities.UserTable;
import com.nagarro.repositories.UserTableRepo;

@Service
public class UserTableServiceImpl implements UserTableService{

	@Autowired
	private UserTableRepo userTableRepo;

	long nextId = 1;
	public long count = 0;
	long total;

	public UserTable addUser(UserTable user) {
		total = userTableRepo.getTotalUserCount();
		long id = total == 0 ? nextId++ : total + 1;
		    user.setId(id);
		    count++;
		    return userTableRepo.save(user);
	}
	
	public long getTotalCount()
	{
		return userTableRepo.getTotalUserCount();
	}
}
