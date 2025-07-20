package dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository;

import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.ScheduledTransactionTemplateEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduledTransactionTemplateRepository
    extends JpaRepository<ScheduledTransactionTemplateEntity, UUID> {}
