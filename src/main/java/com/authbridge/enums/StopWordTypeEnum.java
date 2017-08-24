package com.authbridge.enums;

public enum StopWordTypeEnum {
	
	NAME(1), ADDRESS(2);
	private int value;
	private StopWordTypeEnum(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	

}
