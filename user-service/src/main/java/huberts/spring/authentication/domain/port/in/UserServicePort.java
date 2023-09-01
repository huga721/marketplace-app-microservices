package huberts.spring.authentication.domain.port.in;

import huberts.spring.authentication.adapter.in.web.resource.UserRequest;
import huberts.spring.authentication.domain.model.UserDomainModel;

import java.util.List;

public interface UserServicePort {

    UserDomainModel createUser(UserRequest userRequest);

    List<UserDomainModel> getAllUsers();
    UserDomainModel getUserById(Long userId);

    UserDomainModel updateUserById(Long userId, UserRequest userRequest);
    void deleteUserById(Long userId);
}
