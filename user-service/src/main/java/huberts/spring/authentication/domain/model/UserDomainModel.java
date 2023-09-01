package huberts.spring.authentication.domain.model;

import huberts.spring.authentication.adapter.in.web.resource.UserRequest;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDomainModel {

    private Long id;
    private String username;
    private String keycloakId;
    private String firstName;
    private String lastName;
    private String email;
    private String roleName;

    public void updateUser(UserRequest userRequest) {
        username = userRequest.username();
        firstName = userRequest.firstName();
        lastName = userRequest.lastName();
        email = userRequest.email();
    }
}
