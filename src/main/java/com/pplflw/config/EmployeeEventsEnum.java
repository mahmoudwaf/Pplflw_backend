package com.pplflw.config;

public enum EmployeeEventsEnum {
	ADDED("1"),
	IN_CHECK("2"),
	APPROVED("3"),
	ACTIVE("4");
	
	private String code;
	EmployeeEventsEnum(String code){
		this.code = code;
	}
	
	 public String getCode() {
	        return code;
	 }
	 
	 
	 public  static EmployeeEventsEnum get(String code) {
		 EmployeeEventsEnum event = null;
		 for(EmployeeEventsEnum currentEvent : EmployeeEventsEnum.values()) {
			 if(currentEvent.getCode().equals(code)) {
				 event = currentEvent;
				 break;
			 }
		 }
		 return event;
	 }
}
