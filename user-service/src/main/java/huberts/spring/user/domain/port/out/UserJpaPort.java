package huberts.spring.user.domain.port.out;

import huberts.spring.user.adapter.in.web.resource.UserRequest;
import huberts.spring.user.domain.model.UserDomainModel;

import java.util.List;

public interface UserJpaPort {

    UserDomainModel createUser(UserRequest userRequest, String keycloakId);

    List<UserDomainModel> getAllUsers();
    UserDomainModel getUserById(Long userId);

    UserDomainModel updateUserById(UserDomainModel userDomainModel);

    void deleteUserById(Long userId);

    boolean existUserByUsername(String username);
}
