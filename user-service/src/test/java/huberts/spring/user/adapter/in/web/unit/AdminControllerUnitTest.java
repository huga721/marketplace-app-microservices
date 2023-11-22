package huberts.spring.user.adapter.in.web.unit;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.fasterxml.jackson.databind.ObjectMapper;
import huberts.spring.user.adapter.in.web.AdminUserController;
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
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentScan(basePackages = {"huberts.spring.user.application.security"})
@ExtendWith(SpringExtension.class)
@WebMvcTest(AdminUserController.class)
public class AdminControllerUnitTest {

    private static UserDomainModel defaultUser;

    @MockBean
    private UserServicePort userServicePort;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public static void init() {
        defaultUser = new UserDomainModel();
        defaultUser.setId(1L);
        defaultUser.setUsername("user");
        defaultUser.setKeycloakId("321321-dsasa-cdcsad-xasxas-hhhh");
        defaultUser.setFirstName("Fredi");
        defaultUser.setLastName("Kamionka");
        defaultUser.setEmail("user@gmail.com");
        defaultUser.setRoleName("USER");
    }

    @Test
    @WithJwt("admin.json")
    void shouldEditUser() throws Exception {
        EditRequest editRequest = new EditRequest("newUser", "Foo", "Foo", "f@f.com");
        String editRequestAsString = objectMapper.writeValueAsString(editRequest);

        UserDomainModel userUpdated = new UserDomainModel();
        userUpdated.setUsername(editRequest.username());
        userUpdated.setFirstName(editRequest.firstName());
        userUpdated.setLastName(editRequest.lastName());
        userUpdated.setEmail(editRequest.email());

        Mockito.when(userServicePort.editUserById(anyLong(), any(EditRequest.class)))
                .thenReturn(userUpdated);

        final String link = "/api/user/admin/1";

        mockMvc.perform(MockMvcRequestBuilders.patch(link)
                .contentType(MediaType.APPLICATION_JSON)
                .content(editRequestAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(userUpdated.getUsername()))
                .andExpect(jsonPath("$.firstName").value(userUpdated.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userUpdated.getLastName()))
                .andExpect(jsonPath("$.email").value(userUpdated.getEmail()));
    }

    @Test
    @WithJwt("speedy.json")
    void shouldNotEditUser_WithUserRole() throws Exception {
        EditRequest editRequest = new EditRequest("newUser", "Foo", "Foo", "f@f.com");
        String editRequestAsString = objectMapper.writeValueAsString(editRequest);

        final String link = "/api/user/admin/1";

        mockMvc.perform(MockMvcRequestBuilders.patch(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(editRequestAsString))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldThrowException_WhenRequestedFieldsAreEmpty() throws Exception {
        EditRequest editRequest = new EditRequest("", "Foo", "Foo", "f@f.com");
        String editRequestAsString = objectMapper.writeValueAsString(editRequest);

        UserDomainModel userUpdated = new UserDomainModel();
        userUpdated.setUsername(editRequest.username());
        userUpdated.setFirstName(editRequest.firstName());
        userUpdated.setLastName(editRequest.lastName());
        userUpdated.setEmail(editRequest.email());

        Mockito.when(userServicePort.editUserById(anyLong(), any(EditRequest.class)))
                .thenReturn(userUpdated);

        final String link = "/api/user/admin/1";

        mockMvc.perform(MockMvcRequestBuilders.patch(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(editRequestAsString))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    @WithJwt("admin.json")
    void shouldThrowException_WhenUserNotExist() throws Exception {
        EditRequest editRequest = new EditRequest("dd", "Foo", "Foo", "f@f.com");
        String editRequestAsString = objectMapper.writeValueAsString(editRequest);

        UserDomainModel userUpdated = new UserDomainModel();
        userUpdated.setUsername(editRequest.username());
        userUpdated.setFirstName(editRequest.firstName());
        userUpdated.setLastName(editRequest.lastName());
        userUpdated.setEmail(editRequest.email());

        Mockito.when(userServicePort.editUserById(anyLong(), any(EditRequest.class)))
                .thenThrow(new UserNotFoundException(""));

        final String link = "/api/user/admin/1";

        mockMvc.perform(MockMvcRequestBuilders.patch(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(editRequestAsString))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException));
    }

    @Test
    @WithJwt("admin.json")
    void shouldDeleteUser() throws Exception {
        final String link = "/api/user/admin/1";

        mockMvc.perform(MockMvcRequestBuilders.delete(link))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithJwt("admin.json")
    void shouldNotDeleteUser_WhenUserNotExist() throws Exception {
        Mockito.doThrow(new UserNotFoundException(""))
                .when(userServicePort).deleteUserById(anyLong());

        final String link = "/api/user/admin/1";

        mockMvc.perform(MockMvcRequestBuilders.delete(link))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException));
    }
}
