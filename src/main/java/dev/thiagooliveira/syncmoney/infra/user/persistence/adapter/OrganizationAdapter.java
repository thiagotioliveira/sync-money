package dev.thiagooliveira.syncmoney.infra.user.persistence.adapter;

import dev.thiagooliveira.syncmoney.application.user.domain.dto.CreateOrganizationInput;
import dev.thiagooliveira.syncmoney.application.user.domain.model.Organization;
import dev.thiagooliveira.syncmoney.application.user.domain.port.OrganizationPort;
import dev.thiagooliveira.syncmoney.infra.user.persistence.entity.OrganizationEntity;
import dev.thiagooliveira.syncmoney.infra.user.persistence.repository.OrganizationRepository;

public class OrganizationAdapter implements OrganizationPort {

  private final OrganizationRepository organizationRepository;

  public OrganizationAdapter(OrganizationRepository organizationRepository) {
    this.organizationRepository = organizationRepository;
  }

  @Override
  public Organization save(CreateOrganizationInput input) {
    return this.organizationRepository.save(OrganizationEntity.from(input)).toOrganization();
  }
}
