package com.id.akn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.id.akn.model.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{
	
}
