package dev.thiagooliveira.syncmoney.infra.user.api.mapper;

import dev.thiagooliveira.syncmoney.core.user.domain.model.User;
import dev.thiagooliveira.syncmoney.infra.user.api.dto.GetUsersResponseBody;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  GetUsersResponseBody mapToGetUsersResponseBody(User user);
}
