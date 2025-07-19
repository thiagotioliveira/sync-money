package dev.thiagooliveira.syncmoney.application.user.domain.port;

import dev.thiagooliveira.syncmoney.application.user.domain.dto.CreateOrganizationInput;
import dev.thiagooliveira.syncmoney.application.user.domain.model.Organization;

public interface OrganizationPort {

  Organization save(CreateOrganizationInput input);
}
