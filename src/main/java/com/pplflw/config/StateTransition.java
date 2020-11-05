package com.pplflw.config;

import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

@WithStateMachine
public class StateTransition {
	@OnTransition(target = "ADDED")
	public void doADDED() {
		System.out.println("Application Added");
	}
	
	@OnTransition(target = "IN_CHECK")
	public void doCHECKED_IN() {
		System.out.println("Application In check");
	}
}
