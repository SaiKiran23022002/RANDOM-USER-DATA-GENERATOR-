package com.nagarro.services;

import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class CustomErrorServiceImpl extends RuntimeException implements CustomErrorService{

    private final Map<String, Object> errorResponse;

    public CustomErrorServiceImpl(String message) {
         this.errorResponse = generateErrorResponse(message);
    }
   
    @Override
    public Map<String, Object> getErrorResponse() {
        return errorResponse;
    }

    public static Map<String, Object> generateErrorResponse(String message) {
        Map<String, Object> errorResponse = new LinkedHashMap<>();
        LocalDateTime timestamp = LocalDateTime.parse(LocalDateTime.now().toString(), DateTimeFormatter.ISO_DATE_TIME);
        String formattedTimeStamp = timestamp.format(DateTimeFormatter.ofPattern("dd'th' MMMM yyyy HH:mm:ss"));

        errorResponse.put("message", message);
        errorResponse.put("code", "404");
        errorResponse.put("timestamp", formattedTimeStamp);
        return errorResponse;
    }
}

