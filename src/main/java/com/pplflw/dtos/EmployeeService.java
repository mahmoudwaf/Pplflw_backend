package com.pplflw.dtos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
	@Autowired
	EmployeeRepository employeeRepository;
	
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}
	
	public void updateEmployeeStatusCode(int empId , int statusCode) throws Exception{
		Employee employee = employeeRepository.findById(empId).orElse(null);
		if(employee != null) {
			employee.setStatusCode(statusCode);
			employeeRepository.save(employee);
		}else {
			throw new Exception("Employee ID not exist");
		}
		
	}
}
