package huberts.spring.user.adapter.in.web.integration;

import huberts.spring.user.adapter.out.persistance.entity.UserEntity;
import huberts.spring.user.adapter.out.persistance.repository.UserRepository;
import huberts.spring.user.application.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerIntegrationTest extends ContainerIT {

    private static UserEntity defaultUser;
    private final static Long ID = 1L;
    private final static String USERNAME = "user";
    private final static String KEYCLOAK_ID = "321321-dsasa-cdcsad-xasxas-hhhh";
    private final static String FIRST_NAME = "Fredi";
    private final static String SET_LAST_NAME = "Kamionka";
    private final static String EMAIL = "user@gmail.com";
    private final static String ROLE_NAME = "USER";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository repository;

    @BeforeAll
    public void init() {
        defaultUser = new UserEntity();
        defaultUser.setId(ID);
        defaultUser.setUsername(USERNAME);
        defaultUser.setKeycloakId(KEYCLOAK_ID);
        defaultUser.setFirstName(FIRST_NAME);
        defaultUser.setLastName(SET_LAST_NAME);
        defaultUser.setEmail(EMAIL);
        defaultUser.setRoleName(ROLE_NAME);
        repository.save(defaultUser);
        repository.save(defaultUser);
    }

    @Test
    void shouldReturnUserList_WithNoRole() throws Exception {
        final String link = "/api/user";
        mockMvc.perform(MockMvcRequestBuilders
                        .get(link)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "role-user")
    void shouldReturnUserList_WithUserRole() throws Exception {
        final String link = "/api/user";
        mockMvc.perform(MockMvcRequestBuilders
                        .get(link)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "role-admin")
    void shouldReturnUserList_WithAdminRole() throws Exception {
        final String link = "/api/user";
        mockMvc.perform(MockMvcRequestBuilders
                        .get(link)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnUserById_WithNoRole() throws Exception {
        final String link = "/api/user/1";

        mockMvc.perform(MockMvcRequestBuilders
                        .get(link))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("user"));
    }

    @Test
    @WithMockUser(roles = "role-user")
    void shouldReturnUserById_WithUserRole() throws Exception {
        final String link = "/api/user/1";

        mockMvc.perform(MockMvcRequestBuilders
                        .get(link))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("user"));
    }

    @Test
    @WithMockUser(roles = "role-admin")
    void shouldReturnUserById_WithAdminRole() throws Exception {
        final String link = "/api/user/1";

        mockMvc.perform(MockMvcRequestBuilders
                        .get(link))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("user"));
    }

    @Test
    void shouldThrowException_WhenUserIdNotExist() throws Exception {
        final String link = "/api/user/999";

        mockMvc.perform(MockMvcRequestBuilders
                .get(link))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException));
    }
}
