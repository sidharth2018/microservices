/**
 * 
 */
package com.eazybytes.accounts.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.accounts.clients.CardsClient;
import com.eazybytes.accounts.clients.LoansClient;
import com.eazybytes.accounts.model.Accounts;
import com.eazybytes.accounts.model.Cards;
import com.eazybytes.accounts.model.Customer;
import com.eazybytes.accounts.model.Loans;
import com.eazybytes.accounts.repository.AccountsRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.core.lang.Nullable;


@RestController
public class AccountsController {
	
	@Autowired
	private AccountsRepository accountsRepository;
	
	@Autowired
	private CardsClient cardsClient;
	
	@Autowired LoansClient loansClient;

	@GetMapping("/myaccount")
	public Accounts getAccountDetails(@Nullable @RequestHeader(name = "trace") String trace) {

		System.out.println(trace);
		Accounts accounts = accountsRepository.findByCustomerId(1);
		if (accounts != null) {
			return accounts;
		} else {
			return null;
		}

	}
	
//	@CircuitBreaker(name="detailsForCustomerSupportApp"/*,fallbackMethod = "accountFallback"*/)
	@PostMapping("/myFullDetails")
	public Map<String, Object> fullDetails(@RequestBody Customer customer) {
		Accounts acc = accountsRepository.findByCustomerId(customer.getCustomerId());
		List<Cards> myCards = cardsClient.getCardsDetails(customer);
		List<Loans> myLoans = loansClient.getLoanDetails(customer);
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("Account", acc);
		responseMap.put("Cards", myCards);
		responseMap.put("Loans", myLoans);
		return responseMap;
	}
	
//	public Map<String,Object> accountFallback(Customer customer, Throwable t) {
//		Accounts acc = accountsRepository.findByCustomerId(customer.getCustomerId());
//		List<Cards> myCards = cardsClient.getCardsDetails(customer);
//		List<Loans> myLoans = loansClient.getLoanDetails(customer);
//		Map<String, Object> responseMap = new HashMap<>();
//		responseMap.put("Account", acc);
//		responseMap.put("Cards", myCards);
//		responseMap.put("Loans", myLoans);
//		return responseMap;
//	}

}
