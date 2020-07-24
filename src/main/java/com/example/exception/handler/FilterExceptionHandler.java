package com.example.exception.handler;

import com.example.exception.TenantExtractionException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class FilterExceptionHandler implements ErrorWebExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(FilterExceptionHandler.class);

  @Override
  public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

    ServerHttpResponse httpResponse = exchange.getResponse();
    if (ex instanceof TenantExtractionException) {
      httpResponse.setStatusCode(HttpStatus.BAD_REQUEST);
    } else {
      LOGGER.warn("Unhandled exception: {}", ex.getMessage());
      LOGGER.debug("Unhandled exception: ", ex);
      httpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return httpResponse.writeWith(Mono.fromSupplier(() -> {
      DataBufferFactory bufferFactory = httpResponse.bufferFactory();
      try {
        String errMsgToSend = (httpResponse.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) ? "" : ex.getMessage();
        return bufferFactory.wrap(new ObjectMapper().writeValueAsBytes(errMsgToSend));
      } catch (JsonProcessingException e) {
        return bufferFactory.wrap(new byte[0]);
      }
    }));
  }
}
