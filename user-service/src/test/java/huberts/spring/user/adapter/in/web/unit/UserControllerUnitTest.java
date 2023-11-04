package huberts.spring.user.adapter.in.web.unit;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.fasterxml.jackson.databind.ObjectMapper;
import huberts.spring.user.adapter.in.web.UserController;
import huberts.spring.user.adapter.in.web.resource.EditRequest;
import huberts.spring.user.application.exception.UserNotFoundException;
import huberts.spring.user.domain.model.UserDomainModel;
import huberts.spring.user.domain.port.in.UserServicePort;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentScan(basePackages = {"huberts.spring.user.application.security"})
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerUnitTest {

    private static UserDomainModel defaultUser;
    private final static Long ID = 1L;
    private final static String USERNAME = "user";
    private final static String KEYCLOAK_ID = "321321-dsasa-cdcsad-xasxas-hhhh";
    private final static String FIRST_NAME = "Fredi";
    private final static String SET_LAST_NAME = "Kamionka";
    private final static String EMAIL = "user@gmail.com";
    private final static String ROLE_NAME = "USER";

    @MockBean
    private UserServicePort userServicePort;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

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
    void shouldReturnUserList() throws Exception {
        List<UserDomainModel> users = new ArrayList<>();
        users.add(defaultUser);

        Mockito.when(userServicePort.getAllUsers())
                .thenReturn(users);

        final String link = "/api/user";

        mockMvc.perform(MockMvcRequestBuilders
                        .get(link)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(users.size())));
    }

    @Test
    void shouldReturnEmptyUserList() throws Exception {
        List<UserDomainModel> users = new ArrayList<>();

        Mockito.when(userServicePort.getAllUsers())
                .thenReturn(users);

        final String link = "/api/user";

        mockMvc.perform(MockMvcRequestBuilders
                        .get(link)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(users.size())));
    }

    @Test
    void shouldReturnUserById() throws Exception {
        Mockito.when(userServicePort.getUserById(1L))
                .thenReturn(defaultUser);

        final String link = "/api/user/1";

        mockMvc.perform(MockMvcRequestBuilders
                        .get(link))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(defaultUser.getUsername())));
    }

    @Test
    void shouldThrowException_WhenUserIdNotFound() throws Exception {
        Mockito.when(userServicePort.getUserById(anyLong()))
                .thenThrow(new UserNotFoundException(""));

        final String link = "/api/user/1";

        mockMvc.perform(MockMvcRequestBuilders
                        .get(link))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException));
    }

    @Test
    @WithJwt("walter.json")
    void shouldEditUser_WithUserRole() throws Exception {
        EditRequest editRequest = new EditRequest("newUser", "Foo", "Foo", "f@f.com");
        String editRequestAsString = objectMapper.writeValueAsString(editRequest);

        UserDomainModel userUpdated = new UserDomainModel();
        userUpdated.setUsername(editRequest.username());
        userUpdated.setFirstName(editRequest.firstName());
        userUpdated.setLastName(editRequest.lastName());
        userUpdated.setEmail(editRequest.email());

        Mockito.when(userServicePort.editUserByKeycloakId(anyString(), any(EditRequest.class)))
                .thenReturn(userUpdated);

        final String link = "/api/user";

        mockMvc.perform(MockMvcRequestBuilders
                        .patch(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(editRequestAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(editRequest.username()))
                .andExpect(jsonPath("$.firstName").value(editRequest.firstName()))
                .andExpect(jsonPath("$.lastName").value(editRequest.lastName()))
                .andExpect(jsonPath("$.email").value(editRequest.email()));
    }

    @Test
    @WithJwt("admin.json")
    void shouldEditUser_WithAdminRole() throws Exception {
        EditRequest editRequest = new EditRequest("newUser", "Foo", "Foo", "f@f.com");
        String editRequestAsString = objectMapper.writeValueAsString(editRequest);

        UserDomainModel userUpdated = new UserDomainModel();
        userUpdated.setUsername(editRequest.username());
        userUpdated.setFirstName(editRequest.firstName());
        userUpdated.setLastName(editRequest.lastName());
        userUpdated.setEmail(editRequest.email());

        Mockito.when(userServicePort.editUserByKeycloakId(anyString(), any(EditRequest.class)))
                .thenReturn(userUpdated);

        final String link = "/api/user";

        mockMvc.perform(MockMvcRequestBuilders
                        .patch(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(editRequestAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(editRequest.username()))
                .andExpect(jsonPath("$.firstName").value(editRequest.firstName()))
                .andExpect(jsonPath("$.lastName").value(editRequest.lastName()))
                .andExpect(jsonPath("$.email").value(editRequest.email()));
    }

    @Test
    void shouldNotEditUser_WithNoRole() throws Exception {
        EditRequest editRequest = new EditRequest("newUser", "Foo", "Foo", "f@f.com");
        String editRequestAsString = objectMapper.writeValueAsString(editRequest);

        UserDomainModel userUpdated = new UserDomainModel();
        userUpdated.setUsername(editRequest.username());
        userUpdated.setFirstName(editRequest.firstName());
        userUpdated.setLastName(editRequest.lastName());
        userUpdated.setEmail(editRequest.email());

        final String link = "/api/user";

        mockMvc.perform(MockMvcRequestBuilders
                        .patch(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(editRequestAsString))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithJwt("walter.json")
    void shouldDeleteUser_WithUserRole() throws Exception {
        final String link = "/api/user";

        mockMvc.perform(MockMvcRequestBuilders.delete(link))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotDeleteUser_WithNoRole() throws Exception {
        final String link = "/api/user";

        mockMvc.perform(MockMvcRequestBuilders.delete(link))
                .andExpect(status().isUnauthorized());
    }
}