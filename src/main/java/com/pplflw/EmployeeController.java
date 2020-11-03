package com.pplflw;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import dtos.Employe;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	List<Employe> empList = new ArrayList<Employe>();
	
	@RequestMapping(value = "/addEmployee" ,  method = RequestMethod.POST,consumes = {MediaType.APPLICATION_JSON_VALUE})
	public String addEmployee(@RequestBody Employe employee) {
		empList.add(employee);
		return "Employee saved successfully....";
	}
	
	
	@RequestMapping(value = "/updateEmployeeStatus/{empId}/{statusCode}",method = RequestMethod.GET )
	public String updateEmployeeStatus(@PathVariable("empId") String empId , @PathVariable("statusCode") String statusCode) {
		
		for(Employe emp : empList) {
			if(emp.getEmpId() == Integer.valueOf(empId)) {
				emp.setStatusCode(Integer.valueOf(statusCode));
				break;
			}
		}
		return "Employee status updated successfully";
	}
	
	@RequestMapping(value = "/getEmployee/{empId}" , method = RequestMethod.GET)
	public String getEmployee(@PathVariable("empId") String empId) {
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		for(Employe emp : empList) {
			if(emp.getEmpId() == Integer.valueOf(empId)) {
				try {
					  json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(emp);
					  break;
				}catch(Exception e) {
					e.printStackTrace();
				} 
			}
		}
		//System.out.println("getEmployee:"+json);
		return json;
	}
	
	@GetMapping(value = "/getEmployees" , produces = MediaType.APPLICATION_JSON_VALUE)
	public String getEmployeeList() {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			  json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(empList);
			  System.out.println(json);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
