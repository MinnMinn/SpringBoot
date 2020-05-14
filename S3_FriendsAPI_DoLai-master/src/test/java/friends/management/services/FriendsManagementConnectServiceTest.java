package friends.management.services;

import friends.management.domain.repositoryinterface.FriendsManagementRepository;
import friends.management.exception.AcceptableException;
import friends.management.exception.ConflictException;
import friends.management.exception.EmptyException;
import friends.management.exception.RecordNotFoundException;
import friends.management.response.FriendsManagementSuccessOutput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.*;

@RunWith(SpringRunner.class)
public class FriendsManagementConnectServiceTest {

    @Mock
    private FriendsManagementRepository friendsManagementRepository;

    @InjectMocks
    private FriendsManagementConnectServiceImpl friendsManagementConnectService;

    @Test
    public void connect_success(){
        final Integer userOneId = 1;
        final Integer userTwoId = 2;
        final String emailUserOne = "hoang@gmail.com";
        final String emailUserTwo = "do@gmail.com";
        List<String> emails = new ArrayList<String>();
        emails.add(emailUserOne);
        emails.add(emailUserTwo);
        FriendManagementConnectInput input =
                new FriendManagementConnectInput(emails);
        List<Integer> userIds = new ArrayList<>();
        userIds.add(userOneId);
        userIds.add(userTwoId);
        Mockito.when(this.friendsManagementRepository.getIdsByEmail(input.getFriends()))
                .thenReturn(userIds);
        Mockito.when(this.friendsManagementRepository.isFriend(userOneId,userTwoId)).thenReturn(null);
        Mockito.when(this.friendsManagementRepository.isBlock(userOneId,userTwoId)).thenReturn(Collections.emptyList());
        final FriendsManagementSuccessOutput actual = friendsManagementConnectService.connect(input);
        assertThat(actual).isNotNull();
        assertThat(actual.getSuccess()).isEqualTo(true);
    }
    @Test
    public void connect_inputMissing_Failed(){
        final Integer userOneId = 1;
        final Integer userTwoId = 2;
        final String emailUserOne = "hoang@gmail.com";
        List<String> emails = new ArrayList<String>();
        emails.add(emailUserOne);
        FriendManagementConnectInput input =
                new FriendManagementConnectInput(emails);
        List<Integer> userIds = new ArrayList<>();
        userIds.add(userOneId);
        userIds.add(userTwoId);
        Mockito.when(this.friendsManagementRepository.getIdsByEmail(input.getFriends()))
                .thenReturn(userIds);
        Mockito.when(this.friendsManagementRepository.isBlock(userOneId,userTwoId)).thenReturn(Collections.emptyList());
        assertThatThrownBy(() -> this.friendsManagementConnectService.connect(input))
                .isInstanceOf(EmptyException.class);
    }

    @Test
    public void connect_missingEmail_failedReturn400(){
        final Integer userOneId = 1;
        final String emailUserOne = "hoang@gmail.com";
        final String emailUserTwo = "do@gmail.com";
        List<String> emails = new ArrayList<String>();
        emails.add(emailUserOne);
        emails.add(emailUserTwo);
        FriendManagementConnectInput input =
                new FriendManagementConnectInput(emails);
        List<Integer> userIds = new ArrayList<>();
        userIds.add(userOneId);
        Mockito.when(this.friendsManagementRepository.getIdsByEmail(input.getFriends()))
                .thenReturn(userIds);
        assertThatThrownBy(() -> this.friendsManagementConnectService.connect(input))
                .isInstanceOf(RecordNotFoundException.class);
    }

    @Test
    public void connect_friendHasBeenBlock_failedReturn400(){
        final Integer userOneId = 1;
        final Integer userTwoId = 2;
        final String emailUserOne = "hoang@gmail.com";
        final String emailUserTwo = "do@gmail.com";
        List<String> emails = new ArrayList<String>();
        emails.add(emailUserOne);
        emails.add(emailUserTwo);
        FriendManagementConnectInput input =
                new FriendManagementConnectInput(emails);
        List<Integer> userIds = new ArrayList<>();
        userIds.add(userOneId);
        userIds.add(userTwoId);
        Mockito.when(this.friendsManagementRepository.getIdsByEmail(input.getFriends()))
                .thenReturn(userIds);
        Mockito.when(this.friendsManagementRepository.isFriend(userOneId,userTwoId))
                .thenReturn(null);
        List<Integer> userResult = new ArrayList<>();
        userResult.add(userOneId);
        Mockito.when(this.friendsManagementRepository.isBlock(userOneId,userTwoId)).thenReturn(userResult);
        assertThatThrownBy(() -> this.friendsManagementConnectService.connect(input))
                .isInstanceOf(AcceptableException.class);
    }

    @Test
    public void connect_isFriend_failed(){
        final Integer userOneId = 1;
        final Integer userTwoId = 2;
        final String emailUserOne = "hoang@gmail.com";
        final String emailUserTwo = "do@gmail.com";
        List<String> emails = new ArrayList<String>();
        emails.add(emailUserOne);
        emails.add(emailUserTwo);
        FriendManagementConnectInput input =
                new FriendManagementConnectInput(emails);
        List<Integer> userIds = new ArrayList<>();
        userIds.add(userOneId);
        userIds.add(userTwoId);
        Mockito.when(this.friendsManagementRepository.getIdsByEmail(input.getFriends()))
                .thenReturn(userIds);
        Mockito.when(this.friendsManagementRepository.isFriend(userOneId,userTwoId)).thenReturn(userOneId);
        assertThatThrownBy(() -> this.friendsManagementConnectService.connect(input))
                .isInstanceOf(ConflictException.class);
    }

}
