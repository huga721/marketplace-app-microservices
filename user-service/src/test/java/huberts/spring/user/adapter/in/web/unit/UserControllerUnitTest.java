package huberts.spring.user.adapter.in.web.unit;

import huberts.spring.user.adapter.in.web.UserController;
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
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
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

    @MockBean
    private JwtDecoder jwtDecoder;

    @Autowired
    private MockMvc mockMvc;

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

}