package com.sunbasedata.cms.service;

import java.util.UUID;

import com.sunbasedata.cms.dto.CustomerDto;
import com.sunbasedata.cms.pojo.PageResponse;

public interface CustomerService {

	public CustomerDto createCustomer(CustomerDto customerDto);

	public CustomerDto updateCustomer(UUID id, CustomerDto customerDto);

	public void deleteCustomer(UUID id);

	public PageResponse<CustomerDto> getAllCustomer(Integer pageNumber, Integer pageSize, String sortBy, String orderBy);

	public CustomerDto getCustomerById(UUID id);

	public PageResponse<CustomerDto> searchCustomer(String keywords,String searchBy,Integer pageNumber, Integer pageSize, String sortBy, String orderBy);
}
