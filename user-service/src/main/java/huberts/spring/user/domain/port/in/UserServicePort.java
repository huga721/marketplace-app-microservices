package huberts.spring.user.domain.port.in;

import huberts.spring.user.adapter.in.web.resource.EditRequest;
import huberts.spring.user.adapter.in.web.resource.CreateRequest;
import huberts.spring.user.domain.model.UserDomainModel;

import java.util.List;

public interface UserServicePort {

    UserDomainModel createUser(CreateRequest createRequest);

    List<UserDomainModel> getAllUsers();
    UserDomainModel getUserById(Long userId);

    UserDomainModel editUserByKeycloakId(String keycloakId, EditRequest editRequest);
    UserDomainModel editUserById(Long userId, EditRequest editRequest);

    void deleteUserByKeycloakId(String keycloakId);
    void deleteUserById(Long userId);
}