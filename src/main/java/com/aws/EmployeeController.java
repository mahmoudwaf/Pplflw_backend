package com.aws;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import dtos.Employe;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@GetMapping(value = "/getEmployees" , produces = MediaType.APPLICATION_JSON_VALUE)
	public String getEmployeeList() {
		
		List<Employe> employees = new ArrayList<Employe>();
		Employe emp = new Employe();
		emp.setEmpId(1);
		emp.setFullName("Ahmed Orabi");
		emp.setBirthDate(Date.valueOf("2010-10-10"));
		emp.setDept("Accounting");
		emp.setSalary(1200);
		
		Employe emp2 = new Employe();
		emp2.setEmpId(2);
		emp2.setFullName("Ibrahim Orabi");
		emp2.setBirthDate(Date.valueOf("2009-05-25"));
		emp2.setDept("Development");
		emp2.setSalary(9200);
		
		employees.add(emp);
		employees.add(emp2);
		
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			  json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(employees);
			  System.out.println(json);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
