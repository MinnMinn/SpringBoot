package friends.management.web;

import friends.management.response.FriendsManagementReceiveOutput;
import friends.management.services.FriendsManagementReceiveListService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class FriendsManagementReceiveListControllerTest {
    @Mock
    private FriendsManagementReceiveListService service;
    @InjectMocks
    private FriendsManagementReceiveListController controller;

    @Test
    public void handle_Failed_return500(){
        String emailUserOne = "test@gmail.com";
        String text = "Hello world";
        final ReceiveListRequestForm form = new ReceiveListRequestForm();
        form.setSender(emailUserOne);
        form.setText(emailUserOne);
        when(service.getReceiveList(form)).thenThrow(RuntimeException.class);

        ResponseEntity<FriendsManagementReceiveOutput> actual =
                controller.handle(form);
        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
