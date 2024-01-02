package com.nagarro.services;

import java.util.Map;

public interface CustomErrorService {
	Map<String, Object> getErrorResponse();

    static Map<String, Object> generateErrorResponse(String message) {
		return null;
	}
}
