package com.pplflw.config;

public enum EmployeeStatusEnum {
	ADDED("1"),
	IN_CHECK("2"),
	APPROVED("3"),
	ACTIVE("4");
	
	private String code;
	EmployeeStatusEnum(String code){
		this.code = code;
	}
	
	 public String getCode() {
	        return code;
	 }
	 
	
	/*ADDED,
	IN_CHECK,
	APPROVED,
	ACTIVE*/
	
}
