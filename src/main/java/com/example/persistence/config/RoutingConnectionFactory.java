package com.example.persistence.config;

import org.springframework.data.r2dbc.connectionfactory.lookup.AbstractRoutingConnectionFactory;
import reactor.core.publisher.Mono;

public class RoutingConnectionFactory extends AbstractRoutingConnectionFactory {

  public static final String DB_ROUTING_KEY = "db-routing-key";

  @Override
  protected Mono<Object> determineCurrentLookupKey() {
    System.out.println("Determining current lookup key");
    return Mono.subscriberContext().filter(it -> it.hasKey(DB_ROUTING_KEY)).map(it -> it.get(DB_ROUTING_KEY));
  }
}
