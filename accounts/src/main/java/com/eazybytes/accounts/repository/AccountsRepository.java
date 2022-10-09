package com.eazybytes.accounts.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eazybytes.accounts.model.Accounts;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Repository
public interface AccountsRepository extends CrudRepository<Accounts, Long> {

//	@CircuitBreaker(name="repository",fallbackMethod="repoCallback")
	Accounts findByCustomerId(int customerId);

}
