package com.sunbasedata.cms.exception;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String resourceName;
	private String fieldName;
	private UUID id;
	
	public ResourceNotFoundException(String resourceName, String fieldName, UUID id) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, id));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.id = id;
	}
	
	

}
