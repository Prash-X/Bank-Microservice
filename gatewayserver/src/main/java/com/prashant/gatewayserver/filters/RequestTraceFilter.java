package com.prashant.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * This class is a Spring Cloud Gateway Global Filter. It is a pre-filter which means it will be executed
 * before the request is routed to the target service. This filter is used to generate a correlation id
 * which is used to track the request across multiple services. The correlation id is added as a header
 * in the request and is sent to the target service. If the correlation id is already present in the
 * request headers, then this filter does not do anything. If the correlation id is not present, then
 * it generates a new correlation id and adds it to the request headers.
 */
@Order(1)
@Component //this filter is responsible for generating the correlation id whenever there is a request to the gateway
public class RequestTraceFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

    @Autowired
    FilterUtility filterUtility;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
        if(isCorrelationIdPresent(requestHeaders)){
            logger.debug("Madhousebank-correlation-id found in RequestTraceFilter: {}", filterUtility.getCorrelationId(requestHeaders));
        }else {
            String correlationID = generateCorrelationId();
            exchange = filterUtility.setCorrelationId(exchange,correlationID);
            logger.debug("Madhousebank-correlation-id found in RequestTraceFilter: {}",correlationID);
        }
        return chain.filter(exchange);
    }

    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        if(filterUtility.getCorrelationId(requestHeaders)!=null){
            return true;
        }else{
            return false;
        }
    }

    private String generateCorrelationId(){
        return java.util.UUID.randomUUID().toString();
    }
}
