package com.example.board.model.member;


public enum GenderType {
	MALE("남성"),
	FEMALE("여성");
	
	private String description;
	
	private GenderType(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
