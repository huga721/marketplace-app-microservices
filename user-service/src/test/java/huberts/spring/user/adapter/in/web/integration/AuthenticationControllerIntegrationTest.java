package huberts.spring.user.adapter.in.web.integration;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import huberts.spring.user.adapter.in.web.ContainerIT;
import huberts.spring.user.adapter.in.web.resource.CreateRequest;
import huberts.spring.user.adapter.in.web.resource.LoginRequest;
import huberts.spring.user.adapter.out.persistance.entity.UserEntity;
import huberts.spring.user.adapter.out.persistance.repository.UserRepository;
import huberts.spring.user.application.exception.UserExistException;
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
class AuthenticationControllerIntegrationTest extends ContainerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private UserEntity userWalt;
    private static UserEntity userSpeedy;

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

        userSpeedy = new UserEntity();
        userSpeedy.setId(3L);
        userSpeedy.setUsername("speedy");
        userSpeedy.setKeycloakId("e06919ed-dff5-4ca0-a0ad-ab0ca0a90a88");
        userSpeedy.setFirstName("Matheo");
        userSpeedy.setLastName("Erwin");
        userSpeedy.setEmail("mefake@gmail.com");
        userSpeedy.setRoleName("USER");

        userRepository.save(userWalt);
        userRepository.save(userSpeedy);
    }

    @Test
    void shouldRegisterUser() throws Exception {
        CreateRequest createRequest = new CreateRequest("newUser", "Password", "1", "2", "3@g.com");
        String createRequestAsString = objectMapper.writeValueAsString(createRequest);

        final String linkToRegister = "/api/user/authentication/register";
        final String linkToGetUser = "/api/user/2";

        mockMvc.perform(MockMvcRequestBuilders.post(linkToRegister)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequestAsString))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(createRequest.username()))
                .andExpect(jsonPath("$.firstName").value(createRequest.firstName()));

        mockMvc.perform(MockMvcRequestBuilders.get(linkToGetUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(createRequest.username()));
    }

    @Test
    @WithJwt("keycloak/speedy.json")
    void shouldRegisterUser_WithAnyRole() throws Exception {
        CreateRequest createRequest = new CreateRequest("newUser2", "Password", "1", "2", "3@g.com");
        String createRequestAsString = objectMapper.writeValueAsString(createRequest);

        final String linkToRegister = "/api/user/authentication/register";
        final String linkToGetUser = "/api/user/1";

        mockMvc.perform(MockMvcRequestBuilders.post(linkToRegister)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequestAsString))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.get(linkToGetUser))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotRegister_WhenAnyFieldIsEmpty() throws Exception {
        CreateRequest createRequest = new CreateRequest("", "Password", "", "2", "3@g.com");
        String createRequestAsString = objectMapper.writeValueAsString(createRequest);

        final String link = "/api/user/authentication/register";

        mockMvc.perform(MockMvcRequestBuilders.post(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequestAsString))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void shouldNotRegister_WhenUserWithUsernameExists() throws Exception {
        CreateRequest createRequest = new CreateRequest("walt", "Password", "32", "2", "3@g.com");
        String createRequestAsString = objectMapper.writeValueAsString(createRequest);

        final String link = "/api/user/authentication/register";

        mockMvc.perform(MockMvcRequestBuilders.post(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequestAsString))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserExistException));
    }

    @Test
    void shouldLoginUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest("walt", "password");
        String loginRequestAsString = objectMapper.writeValueAsString(loginRequest);

        final String link = "/api/user/authentication/login";
        final String linkToTestJWT = "/api/user";

        String JWT = mockMvc.perform(MockMvcRequestBuilders.post(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode jsonNode = objectMapper.readTree(JWT);
        String jwt = jsonNode.get("access_token").asText();

        mockMvc.perform(MockMvcRequestBuilders.delete(linkToTestJWT)
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotLogin_WhenLoginRequestIsEmpty() throws Exception {
        LoginRequest loginRequest = new LoginRequest("", "password");
        String loginRequestAsString = objectMapper.writeValueAsString(loginRequest);

        final String link = "/api/user/authentication/login";

        mockMvc.perform(MockMvcRequestBuilders.post(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestAsString))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }
}
