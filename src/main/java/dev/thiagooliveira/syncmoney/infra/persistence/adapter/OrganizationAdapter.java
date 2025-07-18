package dev.thiagooliveira.syncmoney.infra.persistence.adapter;

import dev.thiagooliveira.syncmoney.application.user.domain.Organization;
import dev.thiagooliveira.syncmoney.application.user.dto.CreateOrganizationInput;
import dev.thiagooliveira.syncmoney.application.user.port.OrganizationPort;
import dev.thiagooliveira.syncmoney.infra.persistence.entity.OrganizationEntity;
import dev.thiagooliveira.syncmoney.infra.persistence.repository.OrganizationRepository;

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
