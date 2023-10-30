package huberts.spring.user.adapter.in.web.unit;

import huberts.spring.user.adapter.in.web.UserController;
import huberts.spring.user.adapter.out.persistance.UserJpaAdapter;
import huberts.spring.user.adapter.out.persistance.repository.UserRepository;
import huberts.spring.user.application.UserService;
import huberts.spring.user.application.exception.UserNotFoundException;
import huberts.spring.user.domain.model.UserDomainModel;
import huberts.spring.user.domain.port.in.UserServicePort;
import huberts.spring.user.domain.port.out.UserJpaPort;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    private static UserDomainModel defaultUser;
    private final static Long ID = 1L;
    private final static String USERNAME = "user";
    private final static String KEYCLOAK_ID = "321321-dsasa-cdcsad-xasxas-hhhh";
    private final static String FIRST_NAME = "Fredi";
    private final static String SET_LAST_NAME = "Kamionka";
    private final static String EMAIL = "user@gmail.com";
    private final static String ROLE_NAME = "USER";

    @Mock
    private UserJpaPort userJpaPort;
    @Mock
    private UserServicePort userServicePort;

    @InjectMocks
    private UserService userService;

    @BeforeAll
    public static void init() {
        defaultUser = new UserDomainModel();
        defaultUser.setId(ID);
        defaultUser.setUsername(USERNAME);
        defaultUser.setKeycloakId(KEYCLOAK_ID);
        defaultUser.setFirstName(FIRST_NAME);
        defaultUser.setLastName(SET_LAST_NAME);
        defaultUser.setEmail(EMAIL);
        defaultUser.setRoleName(ROLE_NAME);
    }

    @Test
    void shouldReturnUserList() {
        List<UserDomainModel> users = new ArrayList<>();
        users.add(defaultUser);

        Mockito.when(userJpaPort.getAllUsers())
                .thenReturn(users);

        List<UserDomainModel> fromService = userService.getAllUsers();

        assertThat(fromService)
                .isNotNull()
                .hasSize(users.size())
                .isEqualTo(users);
    }

    @Test
    void shouldReturnEmptyUserList() {
        List<UserDomainModel> users = new ArrayList<>();

        Mockito.when(userJpaPort.getAllUsers())
                .thenReturn(users);

        List<UserDomainModel> fromService = userService.getAllUsers();

        assertThat(fromService)
                .isNotNull()
                .hasSize(users.size());
    }

//    @Test
//    void shouldReturnUserById() {
//        Mockito.when(userJpaPort.getUserById(1L)).thenReturn(defaultUser);
//
//        UserDomainModel fromService = userService.getUserById(1L);
//
//        assertThat(fromService)
//                .isNotNull()
//                .isEqualTo(defaultUser);
//    }

    @Test
    void shouldThrowException_WhenUserIdNotExist() {
//        UserDomainModel us = userService.getUserById(1L);
//
//        assertThat(us)
//                .isNotNull();
        Mockito.when(userJpaPort.getUserById(anyLong())).thenReturn(null);
        Executable executable = () -> userService.getUserById(anyLong());
        assertThrows(UserNotFoundException.class, executable);
    }

}
