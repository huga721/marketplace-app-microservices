package huberts.spring.user.adapter.in.web.integration;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.fasterxml.jackson.databind.ObjectMapper;
import huberts.spring.user.adapter.in.web.resource.EditRequest;
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
    private static UserEntity userSavedWithRole;
    private static UserEntity adminSavedWithRole;
    private static UserEntity userToEdit;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository repository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public void init() {
        defaultUser = new UserEntity();
        defaultUser.setId(1L);
        defaultUser.setUsername("user");
        defaultUser.setKeycloakId("123-3213-32134");
        defaultUser.setFirstName("Fredi");
        defaultUser.setLastName("Kamionka");
        defaultUser.setEmail("user@gmail.com");
        defaultUser.setRoleName("USER");

        userSavedWithRole = new UserEntity();
        userSavedWithRole.setId(2L);
        userSavedWithRole.setUsername("walt");
        userSavedWithRole.setKeycloakId("cbe10031-5ab7-4ff6-b740-e9b001d93dd1");
        userSavedWithRole.setFirstName("Walter");
        userSavedWithRole.setLastName("White");
        userSavedWithRole.setEmail("walt@gmail.com");
        userSavedWithRole.setRoleName("USER");

        adminSavedWithRole = new UserEntity();
        adminSavedWithRole.setId(3L);
        adminSavedWithRole.setUsername("moderator");
        adminSavedWithRole.setKeycloakId("a25bf753-3e95-4c4b-8d71-3bff83ba4ab9");
        adminSavedWithRole.setFirstName("Karol");
        adminSavedWithRole.setLastName("Sztaba");
        adminSavedWithRole.setEmail("ksfake@gmail.com");
        adminSavedWithRole.setRoleName("ADMIN");

        userToEdit = new UserEntity();
        userToEdit.setId(4L);
        userToEdit.setUsername("speedy");
        userToEdit.setKeycloakId("ae885313-4d31-4682-8360-09456c7ac3d7");
        userToEdit.setFirstName("Matheo");
        userToEdit.setLastName("Erwin");
        userToEdit.setEmail("mefake@gmail.com");
        userToEdit.setRoleName("USER");

        repository.save(defaultUser);
        repository.save(defaultUser);
        repository.save(userSavedWithRole);
        repository.save(adminSavedWithRole);
        repository.save(userToEdit);
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

    @Test
    @WithJwt("speedy.json")
    void shouldEditUser_WithUserRole() throws Exception {
        EditRequest editRequest = new EditRequest("Foo", "Boo", "Foo", "fake@fake.com");
        String editRequestAsString = objectMapper.writeValueAsString(editRequest);

        final String link = "/api/user";

        mockMvc.perform(MockMvcRequestBuilders.patch(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(editRequestAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(editRequest.username()))
                .andExpect(jsonPath("$.firstName").value(editRequest.firstName()))
                .andExpect(jsonPath("$.lastName").value(editRequest.lastName()))
                .andExpect(jsonPath("$.email").value(editRequest.email()))
                .andExpect(jsonPath("$.keycloakId").value(userSavedWithRole.getKeycloakId()));
    }

    @Test
    @WithJwt("admin-role.json")
    void shouldEditUser_WithAdminRole() throws Exception {
        EditRequest editRequest = new EditRequest("", "", "Foo", "");
        String editRequestAsString = objectMapper.writeValueAsString(editRequest);

        final String link = "/api/user";

        mockMvc.perform(MockMvcRequestBuilders.patch(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(editRequestAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value(editRequest.lastName()))
                .andExpect(jsonPath("$.keycloakId").value(adminSavedWithRole.getKeycloakId()));
    }

    @Test
    void shouldNotEditUser_WithNoRole() throws Exception {
        EditRequest editRequest = new EditRequest("Foo", "Boo", "Foo", "fake@fake.com");
        String editRequestAsString = objectMapper.writeValueAsString(editRequest);

        final String link = "/api/user";

        mockMvc.perform(MockMvcRequestBuilders.patch(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(editRequestAsString))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithJwt("user-role.json")
    void shouldNotEditUser_WhenFieldsOfRequestBodyAreEmpty() throws Exception {
        EditRequest editRequest = new EditRequest("", "", "", "");
        String editRequestAsString = objectMapper.writeValueAsString(editRequest);

        final String link = "/api/user";

        mockMvc.perform(MockMvcRequestBuilders.patch(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(editRequestAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(userSavedWithRole.getUsername()))
                .andExpect(jsonPath("$.firstName").value(userSavedWithRole.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userSavedWithRole.getLastName()))
                .andExpect(jsonPath("$.email").value(userSavedWithRole.getEmail()))
                .andExpect(jsonPath("$.keycloakId").value(userSavedWithRole.getKeycloakId()));
    }
}
