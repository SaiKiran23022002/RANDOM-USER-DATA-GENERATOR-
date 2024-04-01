package com.nagarro.entities;

import java.time.LocalDateTime; 

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usertable")
public class UserTable {

	@Id
	long id;
	String name;
	int age;
	String gender;
	String dob;
	String nationality;
	String verification_Status;
	LocalDateTime date_created;
	LocalDateTime date_modified;
	
	public UserTable() {}
	
	public UserTable(long id, String name, int age, String gender, String dob, String nationality, LocalDateTime dateCreated, LocalDateTime dateModified, String verificationStatus) {
        this.id=id;
		this.name = name;
        this.age = age;
        this.gender = gender;
        this.dob = dob;
        this.nationality = nationality;
        this.date_created = dateCreated;
        this.date_modified = dateModified;
        this.verification_Status = verificationStatus;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	public String getVerification_Status() {
		return verification_Status;
	}

	public void setVerification_Status(String verification_Status) {
		this.verification_Status = verification_Status;
	}
	
	public LocalDateTime getDate_created() {
		return date_created;
	}

	public void setDate_created(LocalDateTime date_created) {
		this.date_created = date_created;
	}

	public LocalDateTime getDate_modified() {
		return date_modified;
	}

	public void setDate_modified(LocalDateTime date_modified) {
		this.date_modified = date_modified;
	}

}
