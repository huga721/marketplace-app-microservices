package huberts.spring.user.domain.model;

import huberts.spring.user.adapter.in.web.resource.EditRequest;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class UserDomainModel {

    private Long id;
    private String username;
    private String keycloakId;
    private String firstName;
    private String lastName;
    private String email;
    private String roleName;

    public void updateUser(EditRequest request) {
        if (!request.username().isEmpty()) {
            username = request.username();
        }
        if (!request.firstName().isEmpty()) {
            firstName = request.firstName();
        }
        if (!request.lastName().isEmpty()) {
            lastName = request.lastName();
        }
        if (!request.email().isEmpty()) {
            email = request.email();
        }
    }
}