package com.nagarro.tests;

import static org.hamcrest.Matchers.hasSize;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.nagarro.entities.UserTable;
import com.nagarro.repositories.UserTableRepo;
import com.nagarro.services.SortUserServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness=Strictness.LENIENT)
public class TestApiController {

	@InjectMocks
	SortUserServiceImpl sortUserService;
	
	@Mock
	UserTableRepo userTableRepo;
	
	@Autowired
	private MockMvc mockObject;
	
	
	@Test
	void mockTestingForAge() throws Exception{
	
		UserTable userTable1=new UserTable(52,"AlissaMoreau",51,"female","1972-06-22","CH",LocalDateTime.now(),LocalDateTime.now(),"TO_BE_VERIFIED");
		UserTable userTable2=new UserTable(53,"DimitriDa Silva",54,"male","1969-04-29","CH",LocalDateTime.now(),LocalDateTime.now(),"TO_BE_VERIFIED");

		
		List<UserTable> sampleUsersByAgeOdd = new ArrayList<>();
		sampleUsersByAgeOdd.add(userTable1);
		sampleUsersByAgeOdd.add(userTable2);
        when(sortUserService.getSortedUsers("age", "odd", "2", "51")).thenReturn(sampleUsersByAgeOdd);
 
        mockObject.perform(get("http://localhost:8090/api/users?sortType=Age&sortOrder=odd&limit=2&offset=51"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].data.id").value(52));
        
        List<UserTable> sampleUsersByAgeEven = new ArrayList<>();
		sampleUsersByAgeEven.add(userTable1);
		sampleUsersByAgeEven.add(userTable2);
		
        when(sortUserService.getSortedUsers("age", "even", "2", "51")).thenReturn(sampleUsersByAgeEven);
 
        mockObject.perform(get("http://localhost:8090/api/users?sortType=Age&sortOrder=even&limit=2&offset=51"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].data.id").value(53));
		
	}
	
	@Test
	void mockTestingForName() throws Exception{
		
		UserTable userTable1=new UserTable(52,"AlissaMoreau",51,"female","1972-06-22","CH",LocalDateTime.now(),LocalDateTime.now(),"TO_BE_VERIFIED");
		UserTable userTable2=new UserTable(53,"DimitriDa Silva",54,"male","1969-04-29","CH",LocalDateTime.now(),LocalDateTime.now(),"TO_BE_VERIFIED");
		
		List<UserTable> sampleUsersByNameOdd = new ArrayList<>();
		sampleUsersByNameOdd.add(userTable1);
		sampleUsersByNameOdd.add(userTable2);
        when(sortUserService.getSortedUsers("name", "odd", "2", "51")).thenReturn(sampleUsersByNameOdd);
 
        mockObject.perform(get("http://localhost:8090/api/users?sortType=Name&sortOrder=odd&limit=2&offset=51"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].data.id").value(53));
        
        List<UserTable> sampleUsersByNameEven = new ArrayList<>();
		sampleUsersByNameEven.add(userTable1);
		sampleUsersByNameEven.add(userTable2);
		
        when(sortUserService.getSortedUsers("name", "even", "2", "51")).thenReturn(sampleUsersByNameEven);
 
        mockObject.perform(get("http://localhost:8090/api/users?sortType=name&sortOrder=even&limit=2&offset=51"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].data.id").value(52));
		
	}
	
	
	@Test
    public void testInvalidLimitSize() throws Exception {
        mockObject.perform(get("http://localhost:8090/api/users?sortType=name&sortOrder=ODD&limit=6&offset=2"))
		.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }
	
	@Test
    public void testInvalidOffsetSize() throws Exception {
		mockObject.perform(get("http://localhost:8090/api/users?sortType=name&sortOrder=ODD&limit=3&offset=100"))
		.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }
	
	@Test
    public void testCreateRandomUsers() throws Exception {
        String validSize = "{\"size\": 1}";
        mockObject.perform(MockMvcRequestBuilders.post("http://localhost:8090/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validSize))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
 
    @Test
    public void testCreateRandomUsersWithInvalidSize() throws Exception {
        String negativeSize = "{\"size\": -1}";
 
        mockObject.perform(MockMvcRequestBuilders.post("http://localhost:8090/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(negativeSize))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
 
        String exceededSize = "{\"size\": 6}";
 
        mockObject.perform(MockMvcRequestBuilders.post("http://localhost:8090/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(exceededSize))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }
	
}
