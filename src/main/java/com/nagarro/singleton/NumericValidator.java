package com.nagarro.singleton;

public class NumericValidator implements Validator{

	private static NumericValidator instance;

	private NumericValidator() {
	}

	public static NumericValidator getInstance() {
		if (instance == null) {
			instance = new NumericValidator();
		}
		return instance;
	}

	
	public boolean validateInput(String input) {
		return input.matches("\\d+");

	}
}
