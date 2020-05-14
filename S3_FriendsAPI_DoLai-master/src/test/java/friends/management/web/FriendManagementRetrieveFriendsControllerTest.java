package friends.management.web;

import friends.management.response.FriendsManagementListOutput;
import friends.management.services.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class FriendManagementRetrieveFriendsControllerTest {
    @Mock
    private FriendsManagementRetrieveFriendsService service;
    @InjectMocks
    private FriendManagementRetrieveFriendsController controller;

    @Test
    public void handle_Failed_return500(){
        String emailUserOne = "test@gmail.com";
        final UserFriendsListRequestForm form = new UserFriendsListRequestForm();
        form.setEmail(emailUserOne);
        final FriendManagementRetrieveFriendsInput input = form.toInput();
        when(service.getFriends(input)).thenThrow(RuntimeException.class);

        ResponseEntity<FriendsManagementListOutput> actual =
                controller.handle(form);
        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
