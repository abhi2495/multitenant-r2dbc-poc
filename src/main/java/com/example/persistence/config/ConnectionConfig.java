package com.example.persistence.config;

import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration;
import dev.miku.r2dbc.mysql.MySqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConnectionConfig extends AbstractR2dbcConfiguration {

  private final R2dbcConfigProperties r2dbcConfigProperties;

  @Autowired
  public ConnectionConfig(R2dbcConfigProperties r2dbcConfigProperties) {
    this.r2dbcConfigProperties = r2dbcConfigProperties;
  }

  @Override
  @Bean
  public ConnectionFactory connectionFactory() {
    RoutingConnectionFactory connectionFactory = new RoutingConnectionFactory();
    Map<String, ConnectionFactory> factories = new HashMap<>();
    MySqlConnectionConfiguration.Builder configurationBuilder = MySqlConnectionConfiguration.builder()
        .host(r2dbcConfigProperties.getHost())
        .port(r2dbcConfigProperties.getPort())
        .username(r2dbcConfigProperties.getUsername())
        .password(r2dbcConfigProperties.getPassword())
        .serverZoneId(ZoneId.of("UTC").normalized());

    for (String tenant : r2dbcConfigProperties.getTenants()) {
      String databaseName = "myschema_" + tenant;
      factories.put(tenant, MySqlConnectionFactory.from(configurationBuilder.database(databaseName).build()));
    }
    connectionFactory.setTargetConnectionFactories(factories);
    connectionFactory.setDefaultTargetConnectionFactory(MySqlConnectionFactory.from(configurationBuilder.database(
        "default").build()));
    return connectionFactory;
  }
}
