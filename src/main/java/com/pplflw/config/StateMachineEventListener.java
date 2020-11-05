package com.pplflw.config;

import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

public class StateMachineEventListener extends StateMachineListenerAdapter<EmployeeStatusEnum, EmployeeEventsEnum> {
	 @Override
	    public void stateChanged(State<EmployeeStatusEnum, EmployeeEventsEnum> from, State<EmployeeStatusEnum, EmployeeEventsEnum> to) {
		 //System.out.println("From = "+from);
		// System.out.println("To  = " + to );
		 if(from == null) {
			 System.out.println("Application Status changed to be "+to.getId() );
		 }else {
			 System.out.println("Application Status changed from "+from.getId() + " to "+to.getId());
		 }
	    }
}
