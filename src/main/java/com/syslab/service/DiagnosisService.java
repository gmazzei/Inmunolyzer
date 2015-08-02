package com.syslab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syslab.entity.Diagnosis;
import com.syslab.repository.DiagnosisRepository;

@Service
public class DiagnosisService {
	
	@Autowired
	private DiagnosisRepository diagnosisRepository;
	
	public List<Diagnosis> getAll() {
		return (List<Diagnosis>) diagnosisRepository.findAll();
	}
	
	public Diagnosis getDiagnosis(Integer id) {
		return diagnosisRepository.findOne(id);
	}
	
	public void save(Diagnosis diagnosis) {
		diagnosisRepository.save(diagnosis);
	}
	
	public void delete(Diagnosis diagnosis) {
		diagnosisRepository.delete(diagnosis);
	}
	
	public List<Diagnosis> findAllByNameContaining(String name) {
		return diagnosisRepository.findAllByNameContainingOrderByNameAsc(name);
	}
	
	
}
