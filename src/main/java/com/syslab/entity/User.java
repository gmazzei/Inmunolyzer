package com.syslab.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.ajax.json.JsonUtils;
import org.springframework.http.converter.json.GsonBuilderUtils;

import com.google.gson.Gson;

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
	
	
	
	public User() {
	
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
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
		
}
