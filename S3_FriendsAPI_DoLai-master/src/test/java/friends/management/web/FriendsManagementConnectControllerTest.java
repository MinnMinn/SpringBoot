package friends.management.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import friends.management.exception.AcceptableException;
import friends.management.exception.ConflictException;
import friends.management.exception.EmptyException;
import friends.management.exception.RecordNotFoundException;
import friends.management.response.FriendsManagementSuccessOutput;
import friends.management.services.FriendManagementConnectInput;
import friends.management.services.FriendsManagementConnectService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FriendsManagementConnectControllerTest {
    static final String POST_REQUEST_SUFFIX = "/friends-management/connect";
    @Autowired
    ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @Mock
    private FriendsManagementConnectService service;
    @InjectMocks
    private FriendsManagementConnectController controller;
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
    }

    @Test
    public void handle_Failed_return500(){
        String emailUserOne = "test@gmail.com";
        String emailUserTwo = "testTwo@gmail.com";
        final UserFriendsRequestForm form = new UserFriendsRequestForm();
        List<String> emails = new ArrayList<String>();
        emails.add(emailUserOne);
        emails.add(emailUserTwo);
        form.setFriends(emails);
        final FriendManagementConnectInput input = form.toInput();
        when(service.connect(input)).thenThrow(RuntimeException.class);

        ResponseEntity<FriendsManagementSuccessOutput> actual =
                controller.handle(form);
        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Test
    public void handle_emptyFailed_return400() throws Exception{
        String emailUserOne = "testTwo@gmail.com";
        String emailUserTwo = "testTwo@gmail.com";
        List<String> emails = new ArrayList<>();
        emails.add(emailUserOne);
        emails.add(emailUserTwo);
        final FriendManagementConnectInput input = new FriendManagementConnectInput(emails);

        when(this.service.connect(input)).thenThrow(EmptyException.class);

        this.mockMvc
                .perform(
                        post(POST_REQUEST_SUFFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(
                        status().isBadRequest()
                );
    }
    @Test
    public void handle_notFound_return404() throws Exception{
        String emailUserOne = "testTwo@gmail.com";
        String emailUserTwo = "testTwo@gmail.com";
        List<String> emails = new ArrayList<>();
        emails.add(emailUserOne);
        emails.add(emailUserTwo);
        final FriendManagementConnectInput input = new FriendManagementConnectInput(emails);

        when(this.service.connect(input)).thenThrow(RecordNotFoundException.class);

        this.mockMvc
                .perform(
                        post(POST_REQUEST_SUFFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(
                        status().isNotFound()
                );
    }
    @Test
    public void handle_blocked_return406() throws Exception{
        String emailUserOne = "testTwo@gmail.com";
        String emailUserTwo = "testTwo@gmail.com";
        List<String> emails = new ArrayList<>();
        emails.add(emailUserOne);
        emails.add(emailUserTwo);
        final FriendManagementConnectInput input = new FriendManagementConnectInput(emails);

        when(this.service.connect(input)).thenThrow(AcceptableException.class);

        this.mockMvc
                .perform(
                        post(POST_REQUEST_SUFFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(
                        status().isNotAcceptable()
                );
    }
    @Test
    public void handle_already_return409() throws Exception{
        String emailUserOne = "testTwo@gmail.com";
        String emailUserTwo = "testTwo@gmail.com";
        List<String> emails = new ArrayList<>();
        emails.add(emailUserOne);
        emails.add(emailUserTwo);
        final FriendManagementConnectInput input = new FriendManagementConnectInput(emails);

        when(this.service.connect(input)).thenThrow(ConflictException.class);

        this.mockMvc
                .perform(
                        post(POST_REQUEST_SUFFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(
                        status().isConflict()
                );
    }
}
