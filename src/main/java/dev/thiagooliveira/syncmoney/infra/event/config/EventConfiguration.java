package dev.thiagooliveira.syncmoney.infra.event.config;

import dev.thiagooliveira.syncmoney.application.event.EventPublisher;
import dev.thiagooliveira.syncmoney.infra.event.adapter.EventPublisherAdapter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfiguration {

  @Bean
  public EventPublisher eventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    return new EventPublisherAdapter(applicationEventPublisher);
  }
}
