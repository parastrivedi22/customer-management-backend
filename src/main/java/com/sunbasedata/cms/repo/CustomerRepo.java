package com.sunbasedata.cms.repo;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbasedata.cms.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, UUID> {
	public Page<Customer> findByFirstNameContaining(String keywords, Pageable pageable);
	public Page<Customer> findByCityContaining(String keywords, Pageable pageable);
	public Page<Customer> findByEmailContaining(String keywords, Pageable pageable);
	public Page<Customer> findByPhoneContaining(String keywords, Pageable pageable);
}
