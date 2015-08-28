package com.syslab.entity;

public enum Technique {
	
	KI32("KI-32");
	
	private String value;

	private Technique(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
}
