package dev.thiagooliveira.syncmoney.infra.user.api.mapper;

import dev.thiagooliveira.syncmoney.core.user.application.dto.RegisterUserInput;
import dev.thiagooliveira.syncmoney.core.user.domain.model.User;
import dev.thiagooliveira.syncmoney.infra.user.api.dto.PostRegisterRequestBody;
import dev.thiagooliveira.syncmoney.infra.user.api.dto.PostRegisterResponseBody;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

  RegisterUserInput mapToRegisterUserInput(PostRegisterRequestBody postRegisterRequestBody);

  PostRegisterResponseBody mapToPostRegisterResponseBody(User user);
}
