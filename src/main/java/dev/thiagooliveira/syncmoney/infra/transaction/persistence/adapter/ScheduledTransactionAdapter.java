package dev.thiagooliveira.syncmoney.infra.transaction.persistence.adapter;

import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateScheduledTransactionInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.projection.ScheduledTransactionEnriched;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransaction;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransactionTemplate;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.ScheduledTransactionPort;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.ScheduledTransactionEntity;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.ScheduledTransactionTemplateEntity;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.ScheduledTransactionRepository;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.ScheduledTransactionTemplateRepository;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
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

  @Override
  public List<ScheduledTransactionTemplate> findTemplatesByAccountId(
      UUID accountId, YearMonth yearMonth) {
    var from = yearMonth.atDay(1);
    var to = yearMonth.atEndOfMonth();
    return this.scheduledTransactionTemplateRepository
        .findByAccountIdAndStartDateLessThanEqualOrStartDateBetweenAndRecurringIsTrue(
            accountId, from, to)
        .stream()
        .map(ScheduledTransactionTemplateEntity::toScheduledTransactionTemplate)
        .toList();
  }

  @Override
  public List<ScheduledTransactionEnriched> findByAccountId(UUID accountId, YearMonth yearMonth) {
    var from = yearMonth.atDay(1);
    var to = yearMonth.atEndOfMonth();
    return this.scheduledTransactionRepository.findByAccountId(accountId, from, to);
  }

  @Override
  public boolean existsByTemplateIdAndDueDateOriginal(UUID templateId, LocalDate dueDateOriginal) {
    return this.scheduledTransactionRepository.existsByTemplateIdAndDueDateOriginal(
        templateId, dueDateOriginal);
  }
}
