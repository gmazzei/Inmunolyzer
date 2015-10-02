package com.syslab.repository;

import org.springframework.data.repository.CrudRepository;

import com.syslab.entity.Patient;

public interface PatientRepository extends CrudRepository<Patient, Integer> {
	
}
