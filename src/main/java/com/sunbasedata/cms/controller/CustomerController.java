package com.sunbasedata.cms.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sunbasedata.cms.constants.AppConstants;
import com.sunbasedata.cms.dto.CustomerDto;
import com.sunbasedata.cms.pojo.ApiResponse;
import com.sunbasedata.cms.pojo.PageResponse;
import com.sunbasedata.cms.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customer")
//@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/new")
	public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
		CustomerDto customer = customerService.createCustomer(customerDto);
		return new ResponseEntity<>(customer, HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<CustomerDto> updateCustomer(@Valid @RequestBody CustomerDto customerDto,
			@PathVariable UUID id) {
		CustomerDto updatedPost = customerService.updateCustomer(id, customerDto);
		return new ResponseEntity<CustomerDto>(updatedPost, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable UUID id) {
		customerService.deleteCustomer(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("customer deleted", true), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<PageResponse<CustomerDto>> getAllCustomers(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NAUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "orderBy", defaultValue = AppConstants.ORDER_BY, required = false) String orderBy

	) {
		PageResponse<CustomerDto> custumers = customerService.getAllCustomer(pageNumber, pageSize, sortBy, orderBy);
		return new ResponseEntity<PageResponse<CustomerDto>>(custumers, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomerDto> getCustermById(@PathVariable UUID id) {
		CustomerDto customer = customerService.getCustomerById(id);
		return new ResponseEntity<CustomerDto>(customer, HttpStatus.OK);
	}	
	
	
	@GetMapping("/test")
	public String getCustermById() {
		System.out.println("test invoked");
		return "success";
	}

	@GetMapping("/search/{keywords}")
	public ResponseEntity<PageResponse<CustomerDto>> searchCustomer(@PathVariable String keywords,
			@RequestParam(value = "searchBy", defaultValue = AppConstants.SEARCH_BY, required = false) String searchBy,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NAUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "orderBy", defaultValue = AppConstants.ORDER_BY, required = false) String orderBy

	) {
		PageResponse<CustomerDto> custumers = customerService.searchCustomer(keywords, searchBy, pageNumber, pageSize,
				sortBy, orderBy);
		return new ResponseEntity<PageResponse<CustomerDto>>(custumers, HttpStatus.OK);
	}

}
