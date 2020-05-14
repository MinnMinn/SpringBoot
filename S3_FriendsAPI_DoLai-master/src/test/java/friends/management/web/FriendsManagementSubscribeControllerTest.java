package friends.management.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import friends.management.exception.AcceptableException;
import friends.management.exception.ConflictException;
import friends.management.exception.EmptyException;
import friends.management.exception.RecordNotFoundException;
import friends.management.response.FriendsManagementSuccessOutput;
import friends.management.services.FriendsManagementSubscribeService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FriendsManagementSubscribeControllerTest {

    static final String POST_REQUEST_SUFFIX = "/friends-management/subscribe";
    @Autowired
    ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Mock
    private FriendsManagementSubscribeService service;
    @InjectMocks
    private FriendsManagementSubscribeController controller;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
    }

    @Test
    public void handle_Failed_return500(){
        String emailUserOne = "test@gmail.com";
        String emailUserTwo = "testTwo@gmail.com";
        final SubscribeUserRequestForm form = new SubscribeUserRequestForm();
        form.setRequestor(emailUserOne);
        form.setTarget(emailUserTwo);
        when(service.subscribe(form)).thenThrow(RuntimeException.class);

        ResponseEntity<FriendsManagementSuccessOutput> actual =
                controller.handle(form);
        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Test
    public void handle_emptyFailed_return400() throws Exception{
        String emailUserOne = "testTwo@gmail.com";
        String emailUserTwo = "testTwo@gmail.com";
        final SubscribeUserRequestForm form = new SubscribeUserRequestForm();
        form.setRequestor(emailUserOne);
        form.setTarget(emailUserTwo);
        when(this.service.subscribe(form)).thenThrow(EmptyException.class);

        this.mockMvc
                .perform(
                        post(POST_REQUEST_SUFFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(form))
                )
                .andExpect(
                        status().isBadRequest()
                );
    }
    @Test
    public void handle_notFound_return404() throws Exception{
        String emailUserOne = "testTwo@gmail.com";
        String emailUserTwo = "testTwo@gmail.com";
        final SubscribeUserRequestForm form = new SubscribeUserRequestForm();
        form.setRequestor(emailUserOne);
        form.setTarget(emailUserTwo);
        when(this.service.subscribe(form)).thenThrow(RecordNotFoundException.class);

        this.mockMvc
                .perform(
                        post(POST_REQUEST_SUFFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(form))
                )
                .andExpect(
                        status().isNotFound()
                );
    }
    @Test
    public void handle_blocked_return406() throws Exception{
        String emailUserOne = "testTwo@gmail.com";
        String emailUserTwo = "testTwo@gmail.com";
        final SubscribeUserRequestForm form = new SubscribeUserRequestForm();
        form.setRequestor(emailUserOne);
        form.setTarget(emailUserTwo);
        when(this.service.subscribe(form)).thenThrow(AcceptableException.class);

        this.mockMvc
                .perform(
                        post(POST_REQUEST_SUFFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(form))
                )
                .andExpect(
                        status().isNotAcceptable()
                );
    }
    @Test
    public void handle_already_return409() throws Exception{
        String emailUserOne = "testTwo@gmail.com";
        String emailUserTwo = "testTwo@gmail.com";
        final SubscribeUserRequestForm form = new SubscribeUserRequestForm();
        form.setRequestor(emailUserOne);
        form.setTarget(emailUserTwo);
        when(this.service.subscribe(form)).thenThrow(ConflictException.class);

        this.mockMvc
                .perform(
                        post(POST_REQUEST_SUFFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(form))
                )
                .andExpect(
                        status().isConflict()
                );
    }
}
