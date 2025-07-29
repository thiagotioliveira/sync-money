package dev.thiagooliveira.syncmoney.infra.user.persistence.adapter;

import dev.thiagooliveira.syncmoney.core.user.application.dto.CreateOrganizationInput;
import dev.thiagooliveira.syncmoney.core.user.domain.model.Organization;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.OrganizationRepository;
import dev.thiagooliveira.syncmoney.infra.user.persistence.entity.OrganizationEntity;
import dev.thiagooliveira.syncmoney.infra.user.persistence.repository.OrganizationJpaRepository;
import java.util.Optional;
import java.util.UUID;

public class OrganizationRepositoryAdapter implements OrganizationRepository {

  private final OrganizationJpaRepository organizationJpaRepository;

  public OrganizationRepositoryAdapter(OrganizationJpaRepository organizationJpaRepository) {
    this.organizationJpaRepository = organizationJpaRepository;
  }

  @Override
  public Optional<Organization> findById(UUID id) {
    return this.organizationJpaRepository.findById(id).map(OrganizationEntity::toOrganization);
  }

  @Override
  public Organization create(CreateOrganizationInput input) {
    return this.organizationJpaRepository
        .save(OrganizationEntity.create(input))
        .toOrganizationCreated();
  }
}
