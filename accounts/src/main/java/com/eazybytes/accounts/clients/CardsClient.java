package com.eazybytes.accounts.clients;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eazybytes.accounts.model.Cards;
import com.eazybytes.accounts.model.Customer;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@FeignClient("cards")
public interface CardsClient {
	
	@PostMapping("/myCards")
	@CircuitBreaker(name = "cardsClientbreaker",fallbackMethod = "cardsFallback")
	List<Cards> getCardsDetails(@RequestBody Customer customer);
	
	public default List<Cards> cardsFallback(Customer customer,Throwable t){
		
		List<Cards> dummy= new ArrayList<>();
		Cards c1 = new Cards();
		c1.setCardNumber("Dummy from fallback");
		dummy.add(c1);
		return dummy;
		
		
	}

}
