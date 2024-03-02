package com.sunbasedata.cms.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sunbasedata.cms.dto.CustomerDto;
import com.sunbasedata.cms.entity.Customer;
import com.sunbasedata.cms.exception.ResourceNotFoundException;
import com.sunbasedata.cms.pojo.PageResponse;
import com.sunbasedata.cms.repo.CustomerRepo;
import com.sunbasedata.cms.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CustomerRepo customerRepo;

	@Override
	public CustomerDto createCustomer(CustomerDto customerDto) {
		// TODO Auto-generated method stub
		Customer customer = this.toCustomer(customerDto);
		Customer saved = customerRepo.save(customer);
		return this.toDto(saved);
	}

	@Override
	public CustomerDto updateCustomer(UUID id, CustomerDto customerDto) {
		// TODO Auto-generated method stub

		Customer customer = customerRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("customer", "id", id));

		customer.setFirstName(
				customerDto.getFirstName() == null ? customer.getFirstName() : customerDto.getFirstName());
		customer.setLastName(customerDto.getLastName() == null ? customer.getLastName() : customerDto.getLastName());
		customer.setStreet(customerDto.getStreet() == null ? customer.getStreet() : customerDto.getStreet());
		customer.setAddress(customerDto.getAddress() == null ? customer.getAddress() : customerDto.getAddress());
		customer.setCity(customerDto.getCity() == null ? customer.getCity() : customerDto.getCity());
		customer.setState(customerDto.getState() == null ? customer.getState() : customerDto.getState());
		customer.setEmail(customerDto.getEmail() == null ? customer.getEmail() : customerDto.getEmail());
		customer.setPhone(customerDto.getPhone() == null ? customer.getPhone() : customerDto.getPhone());

		Customer updated = customerRepo.save(customer);
		return this.toDto(updated);
	}

	@Override
	public void deleteCustomer(UUID id) {
		Customer customer = customerRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("customer", "id", id));
		customerRepo.delete(customer);
	}

	@Override
	public PageResponse<CustomerDto> getAllCustomer(Integer pageNumber, Integer pageSize, String sortBy,
			String orderBy) {
		Sort sort = orderBy.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Customer> page = this.customerRepo.findAll(pageable);
		List<Customer> customers = page.getContent();
		List<CustomerDto> customerDto = customers.stream().map((customer) -> this.toDto(customer))
				.collect(Collectors.toList());
		PageResponse<CustomerDto> pageResponse = new PageResponse<CustomerDto>();
		pageResponse.setContents(customerDto);
		pageResponse.setPageNumber(page.getNumber());
		pageResponse.setPageSize(page.getSize());
		pageResponse.setTotalElement(page.getTotalElements());
		pageResponse.setTotalPages(page.getTotalPages());
		pageResponse.setLast(page.isLast());
		return pageResponse;
	}

	@Override
	public CustomerDto getCustomerById(UUID id) {
		Customer customer = customerRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("customer", "id", id));
		return this.toDto(customer);
	}

	@Override
	public PageResponse<CustomerDto> searchCustomer(String keywords,String searchBy ,Integer pageNumber, Integer pageSize,
			String sortBy, String orderBy) {

		Sort sort = orderBy.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Customer> page  = null;
		if(searchBy.equalsIgnoreCase("firstName")) {
			 page = this.customerRepo.findByFirstNameContaining(keywords, pageable);
			}
		if(searchBy.equalsIgnoreCase("city")) {
			page = this.customerRepo.findByCityContaining(keywords, pageable);
		}
		if(searchBy.equalsIgnoreCase("email")) {
			page = this.customerRepo.findByEmailContaining(keywords, pageable);
		}
		if(searchBy.equalsIgnoreCase("phone")) {
			page = this.customerRepo.findByPhoneContaining(keywords, pageable);
		}
		
		
		if(page!=null) {
			List<Customer> customers = page.getContent();
			List<CustomerDto> customerDto = customers.stream().map((customer) -> this.toDto(customer))
					.collect(Collectors.toList());
			PageResponse<CustomerDto> pageResponse = new PageResponse<CustomerDto>();
			pageResponse.setContents(customerDto);
			pageResponse.setPageNumber(page.getNumber());
			pageResponse.setPageSize(page.getSize());
			pageResponse.setTotalElement(page.getTotalElements());
			pageResponse.setTotalPages(page.getTotalPages());
			pageResponse.setLast(page.isLast());
			return pageResponse;
		}
		return null;
	}

	// MAPPER METHODS
	private CustomerDto toDto(Customer customer) {
		return this.modelMapper.map(customer, CustomerDto.class);
	}

	private Customer toCustomer(CustomerDto customerDto) {
		return this.modelMapper.map(customerDto, Customer.class);
	}

	

}
