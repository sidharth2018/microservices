package com.mspractice.apigateway;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;
//@Order(1)
//@Component
public class TraceFilter implements GlobalFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		HttpHeaders headers = exchange.getRequest().getHeaders();
		if(headers==null || !headers.containsKey("trace")) {
			
			exchange  = exchange.mutate().request(exchange.getRequest().mutate()
					.header("trace", java.util.UUID.randomUUID().toString()).build()).build();
			
		} 
		else System.out.println(exchange.getRequest().getHeaders().getFirst("trace"));
		
		return chain.filter(exchange);
	}

}
