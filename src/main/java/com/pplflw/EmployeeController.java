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
import com.pplflw.config.EmpUtil;
import com.pplflw.config.EmployeeEventsEnum;
import com.pplflw.config.EmployeeStatusEnum;
import com.pplflw.config.StateMachineEventListener;
import com.pplflw.dtos.Employee;
import com.pplflw.dtos.EmployeeInput;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private StateMachine<EmployeeStatusEnum, EmployeeEventsEnum> stateMachine;
	List<Employee> empList = new ArrayList<Employee>();
	int empIdsCounter = 0;
	 
	@RequestMapping(value = "/addEmployee" ,  method = RequestMethod.POST,consumes = {MediaType.APPLICATION_JSON_VALUE})
	public String addEmployee(@RequestBody EmployeeInput employeeInput) {
		System.out.println("addEmployee() called...");
		Employee employee = EmpUtil.getEmployeeObject(employeeInput);
		employee.setStatusCode(Integer.valueOf(EmployeeStatusEnum.ADDED.getCode()));
		empIdsCounter++;
		employee.setEmpId(empIdsCounter);
		empList.add(employee);
		stateMachine.start();
		stateMachine.sendEvent(EmployeeEventsEnum.ADDED);
		return "Employee saved successfully with employee id " +empIdsCounter ;
	}
	
	 
	 
	 
	@RequestMapping(value = "/updateEmployeeStatus/{empId}/{statusCode}",method = RequestMethod.GET )
	public String updateEmployeeStatus(@PathVariable("empId") String empId , @PathVariable("statusCode") String statusCode) {
		System.out.println("updateEmployeeStatus called empId = "+empId + ",statusCode = "+statusCode);
		boolean  isValidStatusCode = EmpUtil.isValidStatusCode(statusCode);
		if(!isValidStatusCode) {
			return "Invalid Status Code";
		}
		boolean empExist = false;
		for(Employee emp : empList) {
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
		for(Employee emp : empList) {
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
		for(Employee emp : empList) {
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
