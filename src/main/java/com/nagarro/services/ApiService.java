package com.nagarro.services;

import com.nagarro.entities.UserData;
import com.nagarro.entities.UserTable;

import java.io.IOException;
import java.util.List;

import org.springframework.web.reactive.function.client.WebClient;

public interface ApiService {

    List<UserTable> createRandomUsers(int size) throws IOException;

    UserTable createUserTable();

    UserData fetchUserData(long startTime);

    UserTable buildUserTable(UserData userData, List<String> countryIds, String gender);

    boolean isVerified(UserData userData, List<String> countryIds, String gender);

    void logApiTime(long startTime);

    WebClient buildWebClient();
}

