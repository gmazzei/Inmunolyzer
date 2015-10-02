package com.syslab.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "diagnosis")
public class Diagnosis implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
		
	@Column(name = "technique")
	private Technique technique;
	
	@Column(name = "result")
	private Double result;
	
	@Transient
	private Integer goodCellCount;
	
	@Transient
	private Integer badCellCount;
	
	@Transient
	private Double goodCellPercentage;
	
	@Transient
	private Double badCellPercentage;
	
	@Column(name = "creation_date", nullable = false)
	private Date creationDate;
	
	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;
	
	public Diagnosis() {
		this.creationDate = new Date();
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	
	//Getters & Setters	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Technique getTechnique() {
		return technique;
	}

	public void setTechnique(Technique technique) {
		this.technique = technique;
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}
	
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getGoodCellCount() {
		return goodCellCount;
	}

	public void setGoodCellCount(Integer goodCellCount) {
		this.goodCellCount = goodCellCount;
	}

	public Integer getBadCellCount() {
		return badCellCount;
	}

	public void setBadCellCount(Integer badCellCount) {
		this.badCellCount = badCellCount;
	}

	public Double getGoodCellPercentage() {
		return goodCellPercentage;
	}

	public void setGoodCellPercentage(Double goodCellPercentage) {
		this.goodCellPercentage = goodCellPercentage;
	}

	public Double getBadCellPercentage() {
		return badCellPercentage;
	}

	public void setBadCellPercentage(Double badCellPercentage) {
		this.badCellPercentage = badCellPercentage;
	}
	
	
}
