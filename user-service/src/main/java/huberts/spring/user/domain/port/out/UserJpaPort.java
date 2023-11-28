package huberts.spring.user.domain.port.out;

import huberts.spring.user.adapter.in.web.resource.CreateRequest;
import huberts.spring.user.domain.model.UserDomainModel;

import java.util.List;

public interface UserJpaPort {

    UserDomainModel createUser(CreateRequest userRequest, String keycloakId);

    List<UserDomainModel> getAllUsers();
    UserDomainModel getUserById(Long userId);
    UserDomainModel getUserByKeycloakId(String keycloakId);

    void deleteUser(UserDomainModel userDomainModel);
}
