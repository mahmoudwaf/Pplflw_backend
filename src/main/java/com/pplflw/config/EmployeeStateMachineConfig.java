package com.pplflw.config;

import java.util.EnumSet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.StateMachineBuilder.Builder;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachine
public class EmployeeStateMachineConfig extends EnumStateMachineConfigurerAdapter<EmployeeStatusEnum, EmployeeEventsEnum>{
	
	 private static  ApplicationContext context;

	    public   ApplicationContext getApplicationContext() {
	        return context;
	    }

	 
	    public static   void setApplicationContext(ApplicationContext ctx) {
	        context = ctx;
	    }
	public StateMachine<EmployeeStatusEnum, EmployeeEventsEnum> buildMachine() throws Exception {
	 
		Builder<EmployeeStatusEnum, EmployeeEventsEnum> builder = StateMachineBuilder.builder();
		builder.configureStates().withStates().initial(EmployeeStatusEnum.ADDED).states(EnumSet.allOf(EmployeeStatusEnum.class));
		//builder.configureTransitions().withExternal().source(EmployeeStatusEnum.ADDED).target(EmployeeStatusEnum.IN_CHECK).event(EmployeeEventsEnum.IN_CHECK);
		//builder.configureTransitions().withExternal().source(EmployeeStatusEnum.IN_CHECK).target(EmployeeStatusEnum.APPROVED).event(EmployeeEventsEnum.APPROVED);
		//builder.configureTransitions().withExternal().source(EmployeeStatusEnum.APPROVED).target(EmployeeStatusEnum.ACTIVE).event(EmployeeEventsEnum.ACTIVE);
		builder.configureConfiguration().withConfiguration().beanFactory(context.getAutowireCapableBeanFactory());
		 return builder.build();
	}
	
	@Override
	public void configure(StateMachineStateConfigurer<EmployeeStatusEnum, EmployeeEventsEnum> states) throws Exception {
		 states
         .withStates()
             .initial(EmployeeStatusEnum.ADDED)
             .states(EnumSet.allOf(EmployeeStatusEnum.class));
	}
	
	 @Override
	  public void configure(StateMachineTransitionConfigurer<EmployeeStatusEnum, EmployeeEventsEnum> transitions)throws Exception {
	        transitions
	            .withExternal()
	                .source(EmployeeStatusEnum.ADDED).target(EmployeeStatusEnum.IN_CHECK)
	                .event(EmployeeEventsEnum.IN_CHECK)
	                .and()
	            .withExternal()
	                .source(EmployeeStatusEnum.IN_CHECK).target(EmployeeStatusEnum.APPROVED)
	                .event(EmployeeEventsEnum.APPROVED)
	        .and()
            .withExternal()
                .source(EmployeeStatusEnum.APPROVED).target(EmployeeStatusEnum.ACTIVE)
                .event(EmployeeEventsEnum.APPROVED);
	        
	    }
	 
	 
	 
	
}


