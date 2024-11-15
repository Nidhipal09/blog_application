package com.apis.blog_app.utils;

public class ApiResponse {

	private String message;
	private Object response;
	
	public ApiResponse(String message) {
		super();
		this.message = message;
	}
	public ApiResponse(String message, Object response) {
		super();
		this.message = message;
		this.response = response;
	}
	public ApiResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getObject() {
		return response;
	}
	public void setObject(Object response) {
		this.response = response;
	}
	
	
	
	
}
