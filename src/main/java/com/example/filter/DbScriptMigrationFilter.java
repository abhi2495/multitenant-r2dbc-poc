package com.example.filter;
/*
import io.r2dbc.spi.ConnectionFactory;
import name.nkonev.r2dbc.migrate.core.Dialect;
import name.nkonev.r2dbc.migrate.core.R2dbcMigrate;
import name.nkonev.r2dbc.migrate.core.R2dbcMigrateProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


 */
import java.util.Collections;

//@Component
//@Order(-1)
public class DbScriptMigrationFilter {//implements WebFilter {

  /*
  @Autowired
  private ConnectionFactory connectionFactory;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    R2dbcMigrateProperties properties = new R2dbcMigrateProperties();
    properties.setResourcesPaths(Collections.singletonList("classpath:/db/migration/*.sql"));
    properties.setDialect(Dialect.MYSQL);
    return R2dbcMigrate.migrate(connectionFactory, properties).then(chain.filter(exchange));
  }

   */
}
