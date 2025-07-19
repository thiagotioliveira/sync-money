package dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository;

import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.TransactionEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {}
