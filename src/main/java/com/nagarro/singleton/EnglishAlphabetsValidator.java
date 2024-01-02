package com.nagarro.singleton;

import java.util.List; 

import java.util.Arrays;

public class EnglishAlphabetsValidator implements Validator{

	private static EnglishAlphabetsValidator instance;

	private EnglishAlphabetsValidator() {
	}

	public static EnglishAlphabetsValidator getInstance() {
		if (instance == null) {
			instance = new EnglishAlphabetsValidator();
		}
		return instance;
	}

	
	public boolean validateInput(String input) {
		 List<String> validInputs = Arrays.asList("age", "name", "even", "odd");
	        return validInputs.stream()
	                .anyMatch(validInput -> validInput.equalsIgnoreCase(input));
	}

}
