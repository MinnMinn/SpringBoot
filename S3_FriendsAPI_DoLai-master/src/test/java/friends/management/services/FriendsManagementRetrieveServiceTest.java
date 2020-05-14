package friends.management.services;

import friends.management.domain.model.User;
import friends.management.domain.repositoryinterface.FriendsManagementRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class FriendsManagementRetrieveServiceTest {
    @Mock
    private FriendsManagementRepository friendsManagementRepository;

    @InjectMocks
    private FriendsManagementRetrieveServiceImpl service;

    @Test
    public void getFriendList_success(){
        List<User> users = new ArrayList<User>();
        final String emailUserOne = "hoang@gmail.com";
        final String emailUserTwo = "do@gmail.com";
        List<String> emailResult = new ArrayList<>();
        emailResult.add(emailUserOne);
        emailResult.add(emailUserTwo);
        when(friendsManagementRepository.getListFriends()).thenReturn(emailResult);
        ResponseEntity<Map<String, Object>> actual =
                service.handle();
        Map<String, Object> log = new HashMap<>();
        log.put("users" ,emailResult);
        assertThat(actual.getBody()).isEqualTo(log);
    }
}
