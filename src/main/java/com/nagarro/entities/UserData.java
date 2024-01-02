package com.nagarro.entities;

public class UserData {
	
	 String gender;
	 String firstName;
	 String lastName;
	 String dob;
	 int age;
	 String nationality;
	 String name;

	 public UserData(String firstName, String lastName, String nationality, String dob, int age, String gender) {
	        this.firstName = firstName;
	        this.lastName = lastName;
	        this.nationality = nationality;
	        this.dob = dob;
	        this.age = age;
	        this.gender = gender;
	    }

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getName() {
		return firstName + lastName;
	}
	
	public void setName() {
		name=firstName+lastName;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
