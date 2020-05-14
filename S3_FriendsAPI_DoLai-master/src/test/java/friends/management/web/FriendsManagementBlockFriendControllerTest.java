package friends.management.web;

import friends.management.response.FriendsManagementSuccessOutput;
import friends.management.services.FriendsManagementBlockFriendService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class FriendsManagementBlockFriendControllerTest {

    @Mock
    private FriendsManagementBlockFriendService service;
    @InjectMocks
    private FriendsManagementBlockFriendController controller;

    @Test
    public void handle_Failed_return500(){
        String emailUserOne = "test@gmail.com";
        String emailUserTwo = "testTwo@gmail.com";
        final BlockUserRequestForm form = new BlockUserRequestForm();
        form.setRequestor(emailUserOne);
        form.setTarget(emailUserTwo);
        when(service.blockFriend(form)).thenThrow(RuntimeException.class);

        ResponseEntity<FriendsManagementSuccessOutput> actual =
                controller.handle(form);
        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
