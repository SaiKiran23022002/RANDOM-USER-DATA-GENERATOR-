package com.nagarro.controller;

import java.io.IOException;         
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.entities.CombinedData;
import com.nagarro.entities.PageInfo;
import com.nagarro.entities.UserSizeRequest;
import com.nagarro.entities.UserTable;
import com.nagarro.repositories.UserTableRepo;
import com.nagarro.services.ApiServiceImpl;
import com.nagarro.services.CustomErrorServiceImpl;
import com.nagarro.services.SortUserServiceImpl;
import com.nagarro.services.UserTableService;

@RestController
@RequestMapping("/api/users")
public class ApiController {

	ApiServiceImpl apiService;
	UserTableRepo userTableRepo;
	SortUserServiceImpl sortUserService; 
	UserTableService userTableService;

	@Autowired
	public ApiController(ApiServiceImpl apiService, SortUserServiceImpl sortUserService,UserTableRepo userTableRepo,UserTableService userTableService) {
		this.apiService = apiService;
		this.sortUserService= sortUserService;
		this.userTableRepo =userTableRepo;
		this.userTableService=userTableService;
	}
	
	 @PostMapping
	    public ResponseEntity<?> createRandomUsers(@RequestBody UserSizeRequest request) throws IOException {
	        int size = request.getSize();
	        if (size < 1 || size > 5) {
	            return ResponseEntity.badRequest().body(CustomErrorServiceImpl.generateErrorResponse("Invalid size parameter. Size must be from 1 to 5. No characters are allowed."));
	        }

	        List<UserTable> createdUsers = apiService.createRandomUsers(size);
	        return ResponseEntity.ok(createdUsers);
	    }

	 @GetMapping
	    public ResponseEntity<?> getUsers(
	            @RequestParam(value = "sortType") String sortType,
	            @RequestParam(value = "sortOrder") String sortOrder,
	            @RequestParam(value = "limit") String limit,
	            @RequestParam(value = "offset") String offset) {
	        try {
	        	if(Integer.parseInt(offset)<userTableService.getTotalCount()) {
	            List<UserTable> users = sortUserService.getSortedUsers(sortType, sortOrder, limit, offset);
	            List<CombinedData> combinedDataList = users.stream()
	                    .map(user -> new CombinedData(user, new PageInfo().getPageInfo(user.getId(), userTableRepo, new PageInfo())))
	                    .collect(Collectors.toList());
	            return ResponseEntity.ok(combinedDataList);
	        	}
	        	else
	        	{
		            return ResponseEntity.badRequest().body(CustomErrorServiceImpl.generateErrorResponse("Invalid size parameter. Offset Size must be less than total number of users."));

	        	}
	        } catch (CustomErrorServiceImpl ex) {
	            return ResponseEntity.badRequest().body(ex.getErrorResponse());
	        }
	    }
	}

