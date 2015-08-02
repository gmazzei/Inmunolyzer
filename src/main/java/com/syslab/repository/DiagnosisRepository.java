package com.syslab.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.syslab.entity.Diagnosis;

public interface DiagnosisRepository extends CrudRepository<Diagnosis, Integer> {
	
	public List<Diagnosis> findAllByNameContainingOrderByNameAsc(String name);
	
}
