package com.syslab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syslab.entity.Patient;
import com.syslab.repository.PatientRepository;

@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;

	public List<Patient> getAll() {
		return (List<Patient>) patientRepository.findAll();
	}

	public Patient getPatient(Integer id) {
		return patientRepository.findOne(id);
	}

	public void save(Patient patient) {
		patientRepository.save(patient);
	}

	public void delete(Patient patient) {
		patientRepository.delete(patient);
	}

}
