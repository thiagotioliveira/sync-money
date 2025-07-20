package dev.thiagooliveira.syncmoney.infra.transaction.persistence.adapter;

import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateScheduledTransactionInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransaction;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransactionTemplate;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.ScheduledTransactionPort;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.ScheduledTransactionEntity;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.ScheduledTransactionTemplateEntity;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.ScheduledTransactionRepository;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.ScheduledTransactionTemplateRepository;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTransactionAdapter implements ScheduledTransactionPort {

  private final ScheduledTransactionRepository scheduledTransactionRepository;
  private final ScheduledTransactionTemplateRepository scheduledTransactionTemplateRepository;

  public ScheduledTransactionAdapter(
      ScheduledTransactionRepository scheduledTransactionRepository,
      ScheduledTransactionTemplateRepository scheduledTransactionTemplateRepository) {
    this.scheduledTransactionRepository = scheduledTransactionRepository;
    this.scheduledTransactionTemplateRepository = scheduledTransactionTemplateRepository;
  }

  @Override
  public ScheduledTransaction save(ScheduledTransaction scheduledTransaction) {
    return this.scheduledTransactionRepository
        .save(ScheduledTransactionEntity.from(scheduledTransaction))
        .toScheduledTransaction();
  }

  @Override
  public ScheduledTransactionTemplate createTemplate(CreateScheduledTransactionInput input) {
    return this.scheduledTransactionTemplateRepository
        .save(ScheduledTransactionTemplateEntity.from(input))
        .toScheduledTransactionTemplate();
  }
}
