package com.authbridge.constant;

import java.util.ArrayList;
import java.util.List;

public class AuthBridgeEnum {

	public enum Recurring {
		None,
		Everyday,
		Weekly,
		Monthly
		
	}
	
	public enum Type {
		Full_Import,
		Delta_Import
	} 
	
	public static List<String> recurringAsList(){
		List<String> recurring = new ArrayList<String>();
		for (Recurring recurr : Recurring.values()) {
			recurring.add(recurr.toString());
		}
		return recurring;
	}
	
	public static List<String> typeAsList(){
		List<String> typeList = new ArrayList<String>();
		for (Type type : Type.values()) {
			typeList.add(type.toString());
		}
		return typeList;
	}
}
