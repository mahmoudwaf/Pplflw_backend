package com.pplflw;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pplflw.config.EmployeeEventsEnum;
import com.pplflw.config.EmployeeStateMachineConfig;
import com.pplflw.config.EmployeeStatusEnum;
import com.pplflw.config.StateMachineEventListener;
import com.pplflw.dtos.Employe;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private StateMachine<EmployeeStatusEnum, EmployeeEventsEnum> stateMachine;
	List<Employe> empList = new ArrayList<Employe>();
	
	 
	@RequestMapping(value = "/addEmployee" ,  method = RequestMethod.POST,consumes = {MediaType.APPLICATION_JSON_VALUE})
	public String addEmployee(@RequestBody Employe employee) {
		System.out.println("addEmployee() called...");
		empList.add(employee);
		stateMachine.start();
		stateMachine.sendEvent(EmployeeEventsEnum.ADDED);
		return "Employee saved successfully....";
	}
	
	 
	 
	 
	@RequestMapping(value = "/updateEmployeeStatus/{empId}/{statusCode}",method = RequestMethod.GET )
	public String updateEmployeeStatus(@PathVariable("empId") String empId , @PathVariable("statusCode") String statusCode) {
		System.out.println("updateEmployeeStatus called empId = "+empId + ",statusCode = "+statusCode);
		boolean empExist = false;
		for(Employe emp : empList) {
			if(emp.getEmpId() == Integer.valueOf(empId)) {
				emp.setStatusCode(Integer.valueOf(statusCode));
				stateMachine.start();
				stateMachine.sendEvent(EmployeeEventsEnum.get(statusCode));
				empExist = true;
				break;
			}
		}
		String msg = "";
		if(empExist) {
			msg = "Employee status updated successfully";
		}else {
			msg = "Employee not exist,please add it";
		}
		return msg;
	}
	
	@RequestMapping(value = "/getEmployee/{empId}" , method = RequestMethod.GET)
	public String getEmployee(@PathVariable("empId") String empId) {
		System.out.println("getEmployee() called...");
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
		System.out.println("getEmployeeList() called...");
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
	
	@GetMapping(value = "/deleteAll" )
	public String deleteAll() {
		System.out.println("deleteAll() called...");
		for(Employe emp : empList) {
				 empList.remove(emp);
		}
		
		return "Employee List Re-Initialized...";
	}
	
	 @Bean
	    public StateMachineEventListener stateMachineEventListener() {
	        StateMachineEventListener listener = new StateMachineEventListener();
	        stateMachine.addStateListener(listener);
	        return listener;
	    }
}
