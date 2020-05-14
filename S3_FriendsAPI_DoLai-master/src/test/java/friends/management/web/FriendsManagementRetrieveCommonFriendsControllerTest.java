package friends.management.web;

import friends.management.response.FriendsManagementListOutput;
import friends.management.services.FriendManagementConnectInput;
import friends.management.services.FriendsManagementRetrieveCommonFriendsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class FriendsManagementRetrieveCommonFriendsControllerTest {
    @Mock
    private FriendsManagementRetrieveCommonFriendsService service;
    @InjectMocks
    private FriendsManagementRetrieveCommonFriendsController controller;

    @Test
    public void handle_Failed_return500(){
        String emailUserOne = "test@gmail.com";
        String emailUserTwo = "testTwo@gmail.com";
        List<String> emails = new ArrayList<String>();
        emails.add(emailUserOne);
        emails.add(emailUserTwo);
        final UserFriendsRequestForm form = new UserFriendsRequestForm();
        form.setFriends(emails);
        FriendManagementConnectInput input = form.toInput();
        when(service.getCommonFriends(input)).thenThrow(RuntimeException.class);

        ResponseEntity<FriendsManagementListOutput> actual =
                controller.handle(form);
        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
