package com.pplflw.dtos;

import java.util.List;

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
	
	public Employee getEmployee(int empId) {
		Employee emp = employeeRepository.findById(empId).orElse(null);
		return emp;
	}
	public List<Employee> getEmployeeList(){
		List<Employee> empList = employeeRepository.findAll();
		return empList;
	}
}
