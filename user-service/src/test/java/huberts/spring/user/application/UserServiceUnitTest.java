package huberts.spring.user.application;

import huberts.spring.user.adapter.in.web.resource.CreateRequest;
import huberts.spring.user.adapter.in.web.resource.EditRequest;
import huberts.spring.user.application.exception.UserExistException;
import huberts.spring.user.application.exception.UserNotFoundException;
import huberts.spring.user.domain.model.UserDomainModel;
import huberts.spring.user.domain.port.in.KeycloakServicePort;
import huberts.spring.user.domain.port.out.UserJpaPort;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
    private KeycloakServicePort keycloakServicePort;

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

    @Test
    void shouldReturnUserById() {
        Mockito.when(userJpaPort.getUserById(1L)).thenReturn(defaultUser);

        UserDomainModel fromService = userService.getUserById(1L);

        assertThat(fromService)
                .isNotNull()
                .isEqualTo(defaultUser);
    }

    @Test
    void shouldThrowException_WhenUserIdNotExist() {
        when(userJpaPort.getUserById(anyLong())).thenThrow(new UserNotFoundException(""));
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void shouldEditUser() {
        EditRequest editRequest = new EditRequest("edit", "first", "last", "foo@g.com");

        when(userJpaPort.getUserByKeycloakId(anyString()))
                .thenReturn(defaultUser);
        doNothing().when(keycloakServicePort).updateUser(anyString(), any(EditRequest.class));


        UserDomainModel fromService = userService.editUserByKeycloakId("123-123", editRequest);

        assertEquals(fromService.getUsername(), editRequest.username());
        assertEquals(fromService.getFirstName(), editRequest.firstName());
        assertEquals(fromService.getLastName(), editRequest.lastName());
        assertEquals(fromService.getEmail(), editRequest.email());
    }

    @Test
    void shouldNotEditUser_WhenRequestFieldsAreEmpty() {
        EditRequest editRequest = new EditRequest("", "", "", "");

        when(userJpaPort.getUserByKeycloakId(anyString()))
                .thenReturn(defaultUser);

        doNothing().when(keycloakServicePort).updateUser(anyString(), any(EditRequest.class));

        UserDomainModel fromService = userService.editUserByKeycloakId("123-123", editRequest);

        assertEquals(fromService.getUsername(), defaultUser.getUsername());
        assertEquals(fromService.getFirstName(), defaultUser.getFirstName());
        assertEquals(fromService.getLastName(), defaultUser.getLastName());
        assertEquals(fromService.getEmail(), defaultUser.getEmail());
    }

    @Test
    void shouldThrowException_WhenUserNotFoundWithKeycloakId() {
        EditRequest editRequest = new EditRequest("", "", "", "");
        when(userJpaPort.getUserByKeycloakId(anyString()))
                .thenThrow(new UserNotFoundException("Foo"));

        assertThrows(UserNotFoundException.class, () -> userService.editUserByKeycloakId("123-123", editRequest));
    }

    @Test
    void shouldDeleteUser() {
        when(userJpaPort.getUserByKeycloakId(anyString()))
                .thenReturn(defaultUser);

        doNothing().when(userJpaPort).deleteUser(any(UserDomainModel.class));

        userService.deleteUserByKeycloakId("123-123");

        verify(userJpaPort, times(1)).deleteUser(defaultUser);
    }

    @Test
    void shouldThrowException_WhenUserNotExist() {
        when(userJpaPort.getUserByKeycloakId(anyString()))
                .thenThrow(new UserNotFoundException("Foo"));

        assertThrows(UserNotFoundException.class, () -> userService.deleteUserByKeycloakId("123-123"));
    }

    @Test
    void shouldCreateUser() {
        CreateRequest createRequest = new CreateRequest("foo", "foo", "foo", "foo", "fo@fo.com");

        Response response = Response.created(URI.create("/")).build();

        when(keycloakServicePort.createUser(any(CreateRequest.class)))
                .thenReturn(response);
        when(userJpaPort.createUser(any(CreateRequest.class), anyString()))
                .thenReturn(defaultUser);

        UserDomainModel fromService = userService.createUser(createRequest);

        assertNotNull(fromService);
    }

    @Test
    void shouldNotCreateUser_WhenUsernameIsTaken() {
        CreateRequest createRequest = new CreateRequest("foo", "foo", "foo", "foo", "fo@fo.com");

        Response response = Response.status(202).build();

        when(keycloakServicePort.createUser(any(CreateRequest.class)))
                .thenReturn(response);

        assertThrows(UserExistException.class, () -> userService.createUser(createRequest));
    }

    @Test
    void shouldEditUserById() {
        EditRequest editRequest = new EditRequest("test", "test", "test", "test@g.com");

        when(userJpaPort.getUserById(anyLong()))
                .thenReturn(defaultUser);

        UserDomainModel fromService = userService.editUserById(1L, editRequest);

        assertEquals(fromService.getUsername(), editRequest.username());
        assertEquals(fromService.getUsername(), editRequest.firstName());
        assertEquals(fromService.getLastName(), editRequest.lastName());
        assertEquals(fromService.getEmail(), editRequest.email());
    }

    @Test
    void shouldNotEditUser_WhenUserNotExist() {
        EditRequest editRequest = new EditRequest("test", "test", "test", "test@g.com");

        when(userJpaPort.getUserById(anyLong()))
                .thenThrow(new UserNotFoundException("Foo"));

        assertThrows(UserNotFoundException.class, () -> userService.editUserById(1L, editRequest));
    }

    @Test
    void shouldDeleteUserById() {
        when(userJpaPort.getUserById(anyLong()))
                .thenReturn(defaultUser);

        userService.deleteUserById(1L);

        verify(userJpaPort, times(1)).deleteUser(defaultUser);
    }

    @Test
    void shouldNotDeleteUser_WhenUserNotExist() {
        when(userJpaPort.getUserById(anyLong()))
                .thenThrow(new UserNotFoundException("Foo"));

        assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(1L));
    }
}