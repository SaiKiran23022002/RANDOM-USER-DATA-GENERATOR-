package com.nagarro.singleton;

public class ValidatorFactory {

	public Validator createValidator(String parameter) {
		if (parameter.matches("\\d+")) {
			return NumericValidator.getInstance();
		} else {
			return EnglishAlphabetsValidator.getInstance();
		}
	}
}
