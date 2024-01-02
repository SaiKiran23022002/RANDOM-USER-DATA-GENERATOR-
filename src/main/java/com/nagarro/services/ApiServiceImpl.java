package com.nagarro.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.entities.UserData;
import com.nagarro.entities.UserTable;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ApiServiceImpl implements ApiService{

    @Autowired
    private NationalityServiceImpl natObject;

    @Autowired
    private GenderServiceImpl genderObject;

    @Autowired
    private UserTableServiceImpl userTableService;

    private final String baseUrl = "https://randomuser.me/api/";

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public UserTable userTable;

    @Override
    public List<UserTable> createRandomUsers(int size) throws IOException {
        return IntStream.range(0, size)
                .mapToObj(i -> createUserTable())
                .collect(Collectors.toList());
    }

    @Override
    public UserTable createUserTable() {
        long startTime = System.currentTimeMillis();

        UserData userData = fetchUserData(startTime);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        try {
            CompletableFuture<List<String>> nationalityFuture = CompletableFuture
                    .supplyAsync(() -> {
						try {
							return natObject.getNationalityData(userData.getFirstName());
						} catch (IOException e) {
							 e.printStackTrace();
						}
						return null;
					}, executorService);

            CompletableFuture<String> genderFuture = CompletableFuture
                    .supplyAsync(() -> {
						try {
							return genderObject.getGenderData(userData.getFirstName());
						} catch (IOException e) {
							
							e.printStackTrace();
						}
						return null;
					}, executorService);

            CompletableFuture.allOf(nationalityFuture, genderFuture).get();

            List<String> countryIds = nationalityFuture.get();
            String gender = genderFuture.get();

            UserTable userTable = buildUserTable(userData, countryIds, gender);

            userTableService.addUser(userTable);
            return userTable;
        } catch (Exception e) {
            throw new CustomErrorServiceImpl("Error fetching nationality or gender.");
        } finally {
            executorService.shutdown();
        }
    }

    @Override
	public UserData fetchUserData(long startTime) {
        String response = buildWebClient().get().retrieve().bodyToMono(String.class).block();
        logApiTime(startTime);
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(response);
        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON response.", e);
        }

        return new UserData(
                jsonNode.path("results").get(0).path("name").path("first").asText(),
                jsonNode.path("results").get(0).path("name").path("last").asText(),
                jsonNode.path("results").get(0).path("nat").asText(),
                jsonNode.path("results").get(0).path("dob").path("date").asText(),
                jsonNode.path("results").get(0).path("dob").path("age").asInt(),
                jsonNode.path("results").get(0).path("gender").asText()
        );
    }

    @Override
    public UserTable buildUserTable(UserData userData, List<String> countryIds, String gender) {
        String verificationStatus = isVerified(userData, countryIds, gender) ? "VERIFIED" : "TO_BE_VERIFIED";

        		UserTable userTable = new UserTable();
                userTable.setName(userData.getFirstName() + userData.getLastName());
                userTable.setAge(userData.getAge());
                userTable.setGender(userData.getGender());
                userTable.setDob(userData.getDob());
                userTable.setNationality(userData.getNationality());
                userTable.setDate_created(LocalDateTime.now());
                userTable.setDate_modified(LocalDateTime.now());
                userTable.setVerification_Status(verificationStatus);
                return userTable;
                
    
    }

    @Override
	public boolean isVerified(UserData userData, List<String> countryIds, String gender) {
        return userData.getGender().equalsIgnoreCase(gender) && countryIds.contains(userData.getNationality());
    }

    @Override
    public void logApiTime(long startTime) {
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Randomuser.me API = " + totalTime);
    }

    @Override
    public WebClient buildWebClient() {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.newConnection().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000)
                                .responseTimeout(Duration.ofMillis(2000)).doOnConnected(conn -> {
                                    conn.addHandlerLast(new ReadTimeoutHandler(2000, TimeUnit.MILLISECONDS));
                                    conn.addHandlerLast(new WriteTimeoutHandler(2000, TimeUnit.MILLISECONDS));
                                })))
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .baseUrl(baseUrl)
                .build();
    }
}
