package dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository;

import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.ScheduledTransactionEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduledTransactionRepository
    extends JpaRepository<ScheduledTransactionEntity, UUID> {}
