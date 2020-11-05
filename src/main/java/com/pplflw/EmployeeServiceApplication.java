package com.pplflw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.pplflw.config.EmployeeStateMachineConfig;

@SpringBootApplication
public class EmployeeServiceApplication {

	public static void main(String[] args) {
		 
		  ApplicationContext applicationContext = SpringApplication.run(EmployeeServiceApplication.class, args);
		 EmployeeStateMachineConfig.setApplicationContext(applicationContext);

	}

}
