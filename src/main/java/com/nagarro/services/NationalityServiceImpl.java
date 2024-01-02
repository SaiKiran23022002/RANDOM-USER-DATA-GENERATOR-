package com.nagarro.services;

import com.fasterxml.jackson.databind.JsonNode; 
import java.io.IOException;
import java.time.Duration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class NationalityServiceImpl implements NationalityService{

    private final String nationalizeUrl = "https://api.nationalize.io";
    private final WebClient webClient;

    public NationalityServiceImpl() {
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
                .baseUrl(nationalizeUrl)
                .build();
    }

    public List<String> getNationalityData(String name) throws IOException {
        long startTime = System.currentTimeMillis();

        String response = webClient.get().uri("/?name={name}", name)
                .retrieve().bodyToMono(String.class)
                .doOnTerminate(() -> {
                    long endTime = System.currentTimeMillis();
                    long totalTime = endTime - startTime;
                    System.out.println("Nationalize API = " + totalTime);
                })
                .block();
        try {
        	
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);

        return StreamSupport.stream(jsonNode.path("country").spliterator(), false)
                .map(node -> node.path("country_id").asText())
                .collect(Collectors.toList());
        }catch (IOException e) {
		throw new CustomErrorServiceImpl("Error parsing the json string");
        }
    }
}
