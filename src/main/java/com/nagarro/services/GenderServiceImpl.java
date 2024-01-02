package com.nagarro.services;

import java.io.IOException; 
import java.time.Duration;

import java.util.concurrent.TimeUnit;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;


@Service
public class GenderServiceImpl implements GenderService{

    private final String genderizeUrl = "https://api.genderize.io";
    private final WebClient webClient;

    public GenderServiceImpl() {
        this.webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.newConnection()
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                                .responseTimeout(Duration.ofMillis(1000))
                                .doOnConnected(conn -> {
                                    conn.addHandlerLast(new ReadTimeoutHandler(1000, TimeUnit.MILLISECONDS));
                                    conn.addHandlerLast(new WriteTimeoutHandler(1000, TimeUnit.MILLISECONDS));
                                })))
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .baseUrl(genderizeUrl)
                .build();
    }

    @Override
    public String getGenderData(String name) throws IOException {
        long startTime = System.currentTimeMillis();

        String response = webClient.get().uri("/?name={name}", name)
                .retrieve().bodyToMono(String.class)
                .doOnTerminate(() -> {
                    long endTime = System.currentTimeMillis();
                    long totalTime = endTime - startTime;
                    System.out.println("Genderize API = " + totalTime);
                })
                .block();
        try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper .readTree(response);
			return jsonNode.path("gender").asText();
		} catch (IOException e) {
			throw new CustomErrorServiceImpl("Error parsing the json string");
		}
    }
}

