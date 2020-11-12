package com.pplflw;

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
import com.pplflw.dtos.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private StateMachine<EmployeeStatusEnum, EmployeeEventsEnum> stateMachine;
	
	 @Autowired
     EmployeeService empService;
	 
	@RequestMapping(value = "/addEmployee" ,  method = RequestMethod.POST,consumes = {MediaType.APPLICATION_JSON_VALUE})
	public String addEmployee(@RequestBody EmployeeInput employeeInput) {
		System.out.println("addEmployee() called...");
		Employee employee = EmpUtil.getEmployeeObject(employeeInput);
		employee.setStatusCode(Integer.valueOf(EmployeeStatusEnum.ADDED.getCode()));
		employee = empService.saveEmployee(employee);
		stateMachine.start();
		stateMachine.sendEvent(EmployeeEventsEnum.ADDED);
		return "Employee saved successfully with employee id " +employee.getEmpId() ;
	}
	
	 
	 
	 
	@RequestMapping(value = "/updateEmployeeStatus/{empId}/{statusCode}",method = RequestMethod.GET )
	public String updateEmployeeStatus(@PathVariable("empId") String empId , @PathVariable("statusCode") String statusCode) {
		System.out.println("updateEmployeeStatus called empId = "+empId + ",statusCode = "+statusCode);
		boolean  isValidStatusCode = EmpUtil.isValidStatusCode(statusCode);
		if(!isValidStatusCode) {
			return "Invalid Status Code";
		}
		String msg = "";
		try {
			empService.updateEmployeeStatusCode(Integer.valueOf(empId), Integer.valueOf(statusCode));
			msg = "Employee status updated successfully";
		}catch(Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		 
		return msg;
	}
	
	@GetMapping(value = "/getEmployee/{empId}" , produces = MediaType.APPLICATION_JSON_VALUE)
	public String getEmployee(@PathVariable("empId") String empId) {
		System.out.println("getEmployee("+empId+") called...");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
				try {
					 Employee emp = empService.getEmployee(Integer.valueOf(empId));
					 json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(emp);
				}catch(Exception e) {
					e.printStackTrace();
				} 
		return json;
	}
	
	@GetMapping(value = "/getEmployees" , produces = MediaType.APPLICATION_JSON_VALUE)
	public String getEmployeeList() {
		System.out.println("getEmployeeList() called...");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			  List<Employee> empList = empService.getEmployeeList();
			  json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(empList);
			  System.out.println(json);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@GetMapping(value = "/getVersion")
	public String getVersion() {
		return "1.0";
	}
	 
	
	 @Bean
	    public StateMachineEventListener stateMachineEventListener() {
	        StateMachineEventListener listener = new StateMachineEventListener();
	        stateMachine.addStateListener(listener);
	        return listener;
	    }
}
