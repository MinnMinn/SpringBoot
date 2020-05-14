package friends.management.services;

import friends.management.domain.repositoryinterface.FriendsManagementRepository;
import friends.management.exception.AcceptableException;
import friends.management.exception.ConflictException;
import friends.management.exception.EmptyException;
import friends.management.exception.RecordNotFoundException;
import friends.management.response.FriendsManagementSuccessOutput;
import friends.management.web.SubscribeUserRequestForm;
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
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class FriendsManagementSubscribeServiceTest {

    @Mock
    private FriendsManagementRepository friendsManagementRepository;

    @InjectMocks
    private FriendsManagementSubscribeServiceImpl service;

    @Test
    public void subscribe_update_success(){
        final Integer userOneId = 1;
        final Integer userTwoId = 2;
        final String emailUserOne = "hoang@gmail.com";
        final String emailUserTwo = "do@gmail.com";
        final SubscribeUserRequestForm form = new SubscribeUserRequestForm();
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
        Mockito.when(this.friendsManagementRepository.isSubscribe(userOneId,userTwoId))
                .thenReturn(null);
        Mockito.when(this.friendsManagementRepository.isRelationship(userOneId,userTwoId)).thenReturn(userOneId);

        final FriendsManagementSuccessOutput actual = service.subscribe(form);

        assertThat(actual).isNotNull();
    }

    @Test
    public void subscribe_create_success(){
        final Integer userOneId = 1;
        final Integer userTwoId = 2;
        final String emailUserOne = "hoang@gmail.com";
        final String emailUserTwo = "do@gmail.com";
        final SubscribeUserRequestForm form = new SubscribeUserRequestForm();
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
        Mockito.when(this.friendsManagementRepository.isRelationship(userOneId,userTwoId)).thenReturn(null);

        final FriendsManagementSuccessOutput actual = service.subscribe(form);

        assertThat(actual).isNotNull();
    }


    @Test
    public void subscribe_subscribeAlready_failed(){
        final Integer userOneId = 1;
        final Integer userTwoId = 2;
        final String emailUserOne = "hoang@gmail.com";
        final String emailUserTwo = "do@gmail.com";
        final SubscribeUserRequestForm form = new SubscribeUserRequestForm();
        form.setRequestor(emailUserOne);
        form.setTarget(emailUserTwo);
        List<String> emails = new ArrayList<String>();
        emails.add(emailUserOne);
        emails.add(emailUserTwo);
        List<Integer> userIds = new ArrayList<>();
        userIds.add(userOneId);
        userIds.add(userTwoId);
        Mockito.when(this.friendsManagementRepository.getIdsByEmail(emails))
                .thenReturn(userIds);
        Mockito.when(this.friendsManagementRepository.isBlock(userOneId,userTwoId)).thenReturn(Collections.emptyList());
        Mockito.when(this.friendsManagementRepository.isSubscribe(userOneId,userTwoId))
                .thenReturn(userOneId);
        Mockito.when(this.friendsManagementRepository.isRelationship(userOneId,userTwoId)).thenReturn(userOneId);

        assertThatThrownBy(() -> this.service.subscribe(form))
                .isInstanceOf(ConflictException.class);
    }


    @Test
    public void subscribe_userNotExists_failedReturn400() {
        final Integer userOneId = 1;
        final String emailUserOne = "hoang@gmail.com";
        final String emailUserTwo = "do@gmail.com";
        final SubscribeUserRequestForm form = new SubscribeUserRequestForm();
        form.setRequestor(emailUserOne);
        form.setTarget(emailUserTwo);
        List<String> emails = new ArrayList<String>();
        emails.add(emailUserOne);
        emails.add(emailUserTwo);

        FriendManagementConnectInput input =
                new FriendManagementConnectInput(emails);
        List<Integer> userIdsOfOne = new ArrayList<>();
        userIdsOfOne.add(userOneId);
        Mockito.when(this.friendsManagementRepository.getIdsByEmail(input.getFriends()))
                .thenReturn(userIdsOfOne);
        assertThatThrownBy(() -> this.service.subscribe(form))
                .isInstanceOf(RecordNotFoundException.class);
    }

    @Test
    public void subscribe_friendHasBeenBlocked_failedReturn400() {
        final Integer userOneId = 1;
        final Integer userTwoId = 2;
        final String emailUserOne = "hoang@gmail.com";
        final String emailUserTwo = "do@gmail.com";
        final SubscribeUserRequestForm form = new SubscribeUserRequestForm();
        form.setRequestor(emailUserOne);
        form.setTarget(emailUserTwo);

        List<String> emails = new ArrayList<String>();
        emails.add(emailUserOne);
        emails.add(emailUserTwo);
        FriendManagementConnectInput input =
                new FriendManagementConnectInput(emails);
        List<Integer> userIds = new ArrayList<Integer>();
        userIds.add(userOneId);
        userIds.add(userTwoId);
        Mockito.when(this.friendsManagementRepository.getIdsByEmail(input.getFriends()))
                .thenReturn(userIds);
        List<Integer> userIdsOne = new ArrayList<>();
        userIdsOne.add(userOneId);
        Mockito.when(this.friendsManagementRepository.isBlock(userOneId,userTwoId)).thenReturn(userIdsOne);
        assertThatThrownBy(() -> this.service.subscribe(form))
                .isInstanceOf(AcceptableException.class);

    }
}
