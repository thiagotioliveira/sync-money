package dev.thiagooliveira.syncmoney.infra.aop.event;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEventPublisher;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DomainEventScopeAspect {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Around(
      "@annotation(dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEventScoped)")
  public Object manageDomainEventScope(ProceedingJoinPoint pjp) throws Throwable {
    DomainEventPublisher.init();
    try {
      logger.debug("Starting domain scope events to {}", pjp.getSignature());
      return pjp.proceed();
    } finally {
      logger.debug("Cleaning domain scope events to {}", pjp.getSignature());
      DomainEventPublisher.clear();
    }
  }
}
