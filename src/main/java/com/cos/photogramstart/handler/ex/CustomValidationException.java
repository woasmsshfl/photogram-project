package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomValidationException extends RuntimeException{
	
	// 런타임 머신이 객체를 구분할때 사용됨.
	private static final long serialVersionUID = 1L;

	// 컴포지션
	// private String message;
	private Map<String, String> errorMap;
	
	// DI
	public CustomValidationException(String message, Map<String, String> errorMap) {
		super(message);
		this.errorMap = errorMap;
	}
	
	// errorMap GETTER
	public Map<String, String> getErrorMap(){
		return errorMap;
	}
}
