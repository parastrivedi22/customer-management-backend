package com.sunbasedata.cms.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sunbasedata.cms.pojo.ApiResponse;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException ex) {
		String message = ex.getMessage();
		return new ResponseEntity<ApiResponse>(new ApiResponse(message, false), HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> methodArgumentNotValidException(MethodArgumentNotValidException ex){
			Map<String, String> resp = new HashMap<>();
			ex.getBindingResult().getAllErrors().forEach((error)->{
				String fieldName = ((FieldError)error).getField();
				String fieldMessage = error.getDefaultMessage();
				resp.put(fieldName, fieldMessage);
			});
		
		return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
	}
}
