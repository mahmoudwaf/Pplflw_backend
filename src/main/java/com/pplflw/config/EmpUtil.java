package com.pplflw.config;


import com.pplflw.dtos.Employee;
import com.pplflw.dtos.EmployeeInput;

public class EmpUtil {
	
	public static Employee getEmployeeObject(EmployeeInput input) {
		Employee employee = new Employee();
		employee.setBirthDate(input.getBirthDate());
		employee.setFullName(input.getFullName());
		employee.setCountry(input.getCountry());
		employee.setJobTitle(input.getJobTitle());
		return employee;
	}
	
	public static boolean isValidStatusCode(String statusCode) {
		boolean isValidStatusCode = false;
		for(EmployeeStatusEnum status : EmployeeStatusEnum.values()) {
			if(status.getCode().equals( statusCode)){
				isValidStatusCode = true;
				break;
			}
		}
		return isValidStatusCode;		
	}
}
