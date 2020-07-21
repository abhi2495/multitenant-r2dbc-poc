package com.example.filter;

import static com.example.persistence.config.RoutingConnectionFactory.DB_ROUTING_KEY;

import com.example.exception.TenantExtractionException;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@Order(-2)
public class TenancyContextFilter implements WebFilter {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    String tenant = extractTenantFromRequestPath(exchange.getRequest());
    if (tenant != null) {
      return chain.filter(exchange).subscriberContext(context -> context.put(DB_ROUTING_KEY, tenant));
    }
    return Mono.error(new TenantExtractionException());
  }

  private String extractTenantFromRequestPath(ServerHttpRequest request) {
    String applicationPath = request.getPath().pathWithinApplication().value();
    AntPathMatcher matcher = new AntPathMatcher();
    matcher.setCaseSensitive(false);
    if (matcher.match("/{tenant}/api/**", applicationPath)) {
      Map<String, String> templateVariables = matcher.extractUriTemplateVariables("/{tenant}/api/**", applicationPath);
      return templateVariables.get("tenant");
    }
    return null;
  }

}
