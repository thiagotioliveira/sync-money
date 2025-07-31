package dev.thiagooliveira.syncmoney.infra.user.api.mapper;

import dev.thiagooliveira.syncmoney.core.user.application.dto.InvitationInput;
import dev.thiagooliveira.syncmoney.core.user.domain.model.Invitation;
import dev.thiagooliveira.syncmoney.core.user.domain.model.User;
import dev.thiagooliveira.syncmoney.infra.user.api.dto.GetInvitationsResponseBody;
import dev.thiagooliveira.syncmoney.infra.user.api.dto.GetUsersResponseBody;
import dev.thiagooliveira.syncmoney.infra.user.api.dto.PostInviteUserRequestBody;
import dev.thiagooliveira.syncmoney.infra.user.api.dto.PostInviteUserResponseBody;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  GetUsersResponseBody mapToGetUsersResponseBody(User user);

  InvitationInput mapToInvitationInput(
      UUID invitedBy, UUID organizationId, PostInviteUserRequestBody postInviteUserRequestBody);

  PostInviteUserResponseBody mapToPostInviteUserResponseBody(Invitation invitation);

  GetInvitationsResponseBody mapToGetInvitationsResponseBody(Invitation invitation);
}
