package com.syslab.entity;

public enum Technique {
	
	KI67("KI-67");
	
	private String value;

	private Technique(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
}
