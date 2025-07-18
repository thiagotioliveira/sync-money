package dev.thiagooliveira.syncmoney.application.user.port;

import dev.thiagooliveira.syncmoney.application.user.domain.Organization;
import dev.thiagooliveira.syncmoney.application.user.dto.CreateOrganizationInput;

public interface OrganizationPort {

  Organization save(CreateOrganizationInput input);
}
