package friends.management.services;

import friends.management.domain.repositoryinterface.FriendsManagementRepository;
import friends.management.exception.AcceptableException;
import friends.management.exception.RecordNotFoundException;
import friends.management.response.FriendsManagementSuccessOutput;
import friends.management.web.BlockUserRequestForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
public class FriendsManagementBlockFriendServiceTest {

    @Mock
    private FriendsManagementRepository friendsManagementRepository;

    @InjectMocks
    private FriendsManagementBlockFriendServiceImpl service;

    @Test
    public void blockFriend_update_success(){
        final Integer userOneId = 1;
        final Integer userTwoId = 2;
        final String emailUserOne = "hoang@gmail.com";
        final String emailUserTwo = "do@gmail.com";
        final BlockUserRequestForm form = new BlockUserRequestForm();
        form.setRequestor(emailUserOne);
        form.setTarget(emailUserTwo);
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

        Mockito.when(this.friendsManagementRepository.isBlock(userOneId,userTwoId)).thenReturn(Collections.emptyList());
        Mockito.when(this.friendsManagementRepository.isRelationship(userOneId,userTwoId)).thenReturn(userOneId);

        final FriendsManagementSuccessOutput actual = service.blockFriend(form);

        assertThat(actual).isNotNull();
        assertThat(actual.getSuccess()).isEqualTo(true);
    }

    @Test
    public void blockFriend_create_success(){
        final Integer userOneId = 1;
        final Integer userTwoId = 2;
        final String emailUserOne = "hoang@gmail.com";
        final String emailUserTwo = "do@gmail.com";
        final BlockUserRequestForm form = new BlockUserRequestForm();
        form.setRequestor(emailUserOne);
        form.setTarget(emailUserTwo);
        List<String> emails = new ArrayList<String>();
        emails.add(emailUserOne);
        emails.add(emailUserTwo);
        List<Integer> userIds = new ArrayList<>();
        userIds.add(userOneId);
        userIds.add(userTwoId);
        FriendManagementConnectInput input =
                new FriendManagementConnectInput(emails);
        Mockito.when(this.friendsManagementRepository.getIdsByEmail(input.getFriends()))
                .thenReturn(userIds);

        Mockito.when(this.friendsManagementRepository.isBlock(userOneId,userTwoId)).thenReturn(Collections.emptyList());
        Mockito.when(this.friendsManagementRepository.isRelationship(userOneId,userTwoId)).thenReturn(null);

        final FriendsManagementSuccessOutput actual = service.blockFriend(form);

        assertThat(actual).isNotNull();
        assertThat(actual.getSuccess()).isEqualTo(true);
    }

    @Test
    public void blockFriend_userNotExists_failedReturn400(){
        final Integer userOneId = 1;
        final String emailUserOne = "hoang@gmail.com";
        final String emailUserTwo = "do@gmail.com";
        final BlockUserRequestForm form = new BlockUserRequestForm();
        form.setRequestor(emailUserOne);
        form.setTarget(emailUserTwo);

        List<String> emails = new ArrayList<String>();
        emails.add(emailUserOne);
        emails.add(emailUserTwo);

        FriendManagementConnectInput input =
                new FriendManagementConnectInput(emails);
        List<Integer> userIds = new ArrayList<>();
        userIds.add(userOneId);
        Mockito.when(this.friendsManagementRepository.getIdsByEmail(input.getFriends()))
                .thenReturn(userIds);
        assertThatThrownBy(() -> this.service.blockFriend(form))
                .isInstanceOf(RecordNotFoundException.class);
    }

    @Test
    public void blockFriend_friendHasBeenBlocked_failedReturn400(){
        final Integer userOneId = 1;
        final Integer userTwoId = 2;
        final String emailUserOne = "hoang@gmail.com";
        final String emailUserTwo = "do@gmail.com";
        final BlockUserRequestForm form = new BlockUserRequestForm();
        form.setRequestor(emailUserOne);
        form.setTarget(emailUserTwo);
        List<String> emails = new ArrayList<String>();
        emails.add(emailUserOne);
        emails.add(emailUserTwo);
        List<Integer> userIds = new ArrayList<Integer>();
        userIds.add(userOneId);
        userIds.add(userTwoId);

        FriendManagementConnectInput input =
                new FriendManagementConnectInput(emails);
        Mockito.when(this.friendsManagementRepository.getIdsByEmail(input.getFriends()))
                .thenReturn(userIds);
        List<Integer> userResult = new ArrayList<>();
        userResult.add(userOneId);
        Mockito.when(this.friendsManagementRepository.isBlock(userOneId,userTwoId)).thenReturn(userResult);
        assertThatThrownBy(() -> this.service.blockFriend(form))
                .isInstanceOf(AcceptableException.class);
    }
}
