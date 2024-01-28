package huberts.spring.user.adapter.in.web.integration;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.fasterxml.jackson.databind.ObjectMapper;
import huberts.spring.user.adapter.in.web.ContainerIT;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdminControllerIntegrationTest extends ContainerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private UserEntity userWalt;
    private UserEntity userAdmin;
    private UserEntity userSpeedy;
    private UserEntity userToDelete;

    @BeforeAll
    public void init() {
        userWalt = new UserEntity();
        userWalt.setId(1L);
        userWalt.setUsername("walt");
        userWalt.setKeycloakId("cbe10031-5ab7-4ff6-b740-e9b001d93dd1");
        userWalt.setFirstName("Walter");
        userWalt.setLastName("White");
        userWalt.setEmail("walt@gmail.com");
        userWalt.setRoleName("USER");

        userAdmin = new UserEntity();
        userAdmin.setId(2L);
        userAdmin.setUsername("moderator");
        userAdmin.setKeycloakId("caedd3a9-062b-457b-94a1-b893f0b21b4e");
        userAdmin.setFirstName("Karol");
        userAdmin.setLastName("Sztaba");
        userAdmin.setEmail("ksfake@gmail.com");
        userAdmin.setRoleName("ADMIN");

        userSpeedy = new UserEntity();
        userSpeedy.setId(3L);
        userSpeedy.setUsername("speedy");
        userSpeedy.setKeycloakId("e06919ed-dff5-4ca0-a0ad-ab0ca0a90a88");
        userSpeedy.setFirstName("Matheo");
        userSpeedy.setLastName("Erwin");
        userSpeedy.setEmail("mefake@gmail.com");
        userSpeedy.setRoleName("USER");

        userToDelete = new UserEntity();
        userToDelete.setId(4L);
        userToDelete.setUsername("giver");
        userToDelete.setKeycloakId("ae885313-4d31-4682-8360-09456c7ac312");
        userToDelete.setFirstName("Fredi");
        userToDelete.setLastName("Kamionka");
        userToDelete.setEmail("fkfake@gmail.com");
        userToDelete.setRoleName("USER");

        userRepository.save(userWalt);
        userRepository.save(userSpeedy);
        userRepository.save(userToDelete);
        userRepository.save(userToDelete);
        userRepository.save(userAdmin);
    }

    @Test
    @WithJwt("keycloak/admin.json")
    void shouldEditUserById_WithUserAdminRole() throws Exception {
        EditRequest editRequest = new EditRequest("Foo1", "Boo", "Foo", "fake@fake.com");
        String editRequestAsString = objectMapper.writeValueAsString(editRequest);

        final String link = "/api/user/admin/1";

        mockMvc.perform(MockMvcRequestBuilders.patch(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(editRequestAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value(editRequest.username()))
                .andExpect(jsonPath("$.firstName").value(editRequest.firstName()))
                .andExpect(jsonPath("$.lastName").value(editRequest.lastName()))
                .andExpect(jsonPath("$.email").value(editRequest.email()))
                .andExpect(jsonPath("$.keycloakId").value(userWalt.getKeycloakId()));
    }

    @Test
    @WithJwt("keycloak/admin.json")
    void shouldThrowException_WhenUserNotFound() throws Exception {
        EditRequest editRequest = new EditRequest("Foo1", "Boo", "Foo", "fake@fake.com");
        String editRequestAsString = objectMapper.writeValueAsString(editRequest);

        final String link = "/api/user/admin/999";

        mockMvc.perform(MockMvcRequestBuilders.patch(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(editRequestAsString))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException));
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldNotEditUser_WhenUserIsNotAdmin() throws Exception {
        EditRequest editRequest = new EditRequest("Foo1", "Boo", "Foo", "fake@fake.com");
        String editRequestAsString = objectMapper.writeValueAsString(editRequest);

        final String link = "/api/user/admin/1";

        mockMvc.perform(MockMvcRequestBuilders.patch(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(editRequestAsString))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithJwt("keycloak/admin.json")
    void shouldThrowException_WhenRequestBodyFieldsAreEmpty() throws Exception {
        EditRequest editRequest = new EditRequest("", "", "", "");
        String editRequestAsString = objectMapper.writeValueAsString(editRequest);

        final String link = "/api/user/admin/1";

        mockMvc.perform(MockMvcRequestBuilders.patch(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(editRequestAsString))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    @WithJwt("keycloak/admin.json")
    void shouldDeleteUser_WithAdminRole() throws Exception {
        final String deleteUserLink = "/api/user/admin/3";
        final String getUserLink = "/api/user/3";


        mockMvc.perform(MockMvcRequestBuilders.get(getUserLink))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.username").value(userToDelete.getUsername()));

        mockMvc.perform(MockMvcRequestBuilders.delete(deleteUserLink))
                .andExpect(status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get(getUserLink))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithJwt("keycloak/admin.json")
    void shouldThrowException_WhenUserDoesntExist() throws Exception {
        final String link = "/api/user/admin/999";

        mockMvc.perform(MockMvcRequestBuilders.delete(link))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException));

    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldNotDeleteUser_WithUserRole() throws Exception {
        final String link = "/api/user/admin/999";

        mockMvc.perform(MockMvcRequestBuilders.delete(link))
                .andExpect(status().isForbidden());
    }
}