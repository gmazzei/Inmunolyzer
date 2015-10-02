package com.syslab.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "is_admin")
	private boolean admin;
	
	@Column(name = "creation_date", nullable = false)
	private Date creationDate;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
	private List<Patient> patients;
	
	
	public User() {
		this.creationDate = new Date();
	}

	@Override
	public String toString() {
		return this.username;
	}

	public List<Diagnosis> getDiagnoses() {
		List<Diagnosis> diagnoses = new ArrayList();
		for (Patient patient : this.patients) {
			diagnoses.addAll(patient.getDiagnoses());
		}
		return diagnoses;
	}
	
	
	//Getters & Setters

	public String getUsername() {
		return username;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}
	
}
