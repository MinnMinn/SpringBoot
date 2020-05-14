package friends.management.services;

import friends.management.domain.repositoryinterface.FriendsManagementRepository;
import friends.management.exception.EmptyException;
import friends.management.exception.RecordNotFoundException;
import friends.management.response.FriendsManagementListOutput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class FriendsManagementRetrieveCommonFriendsServiceTest {

      @Mock private FriendsManagementRepository friendsManagementRepository;

      @InjectMocks
      private FriendsManagementRetrieveCommonFriendsServiceImpl
          friendsManagementRetrieveCommonFriendsService;

      @Test
      public void commonFriends_success() {
        final Integer userOneId = 1;
        final Integer userTwoId = 2;
        final Integer userThreeId = 3;
        final String emailUserOne = "hoang@gmail.com";
        final String friendCommon = "common@gmail.com";
        final String emailUserTwo = "do@gmail.com";
        List<String> emails = new ArrayList<String>();
        emails.add(emailUserOne);
        emails.add(emailUserTwo);
        List<Integer> userIdsFriendOfOne = new ArrayList<>();
        userIdsFriendOfOne.add(userTwoId);
        userIdsFriendOfOne.add(userThreeId);
        List<Integer> userIdsFriendOfTwo = new ArrayList<>();
        userIdsFriendOfTwo.add(userOneId);
        userIdsFriendOfTwo.add(userThreeId);
        FriendManagementConnectInput input = new FriendManagementConnectInput(emails);
        when(friendsManagementRepository.getIdsByEmail(input.getFriends()))
            .thenReturn(userIdsFriendOfOne);
        when(friendsManagementRepository.getIdByEmail(emailUserOne)).thenReturn(userOneId);
        when(friendsManagementRepository.getIdByEmail(emailUserTwo)).thenReturn(userTwoId);
        when(friendsManagementRepository.getFriendIdsByUserId(userOneId))
            .thenReturn(userIdsFriendOfOne);
        when(friendsManagementRepository.getFriendIdsByUserId(userTwoId))
            .thenReturn(userIdsFriendOfTwo);
        List<Integer> userIdsThree = new ArrayList<>();
        userIdsThree.add(userThreeId);
        List<String> friendCommonEmail = new ArrayList<>();
        friendCommonEmail.add(friendCommon);
        when(friendsManagementRepository.getFriendListByUserId(userIdsThree))
            .thenReturn(friendCommonEmail);

        FriendsManagementListOutput actual =
            friendsManagementRetrieveCommonFriendsService.getCommonFriends(input);
        assertThat(actual).isNotNull();
        assertThat(actual.getFriends()).isEqualTo(friendCommonEmail);
        assertThat(actual.getSuccess()).isEqualTo(true);
      }
      @Test
      public void commonFriends_wrongEmail_success() {
        final Integer userTwoId = 2;
        final String emailUserOne = "hoang@gmail.com";
        final String emailUserTwo = "do@gmail.com";
        List<String> emails = new ArrayList<String>();
        emails.add(emailUserOne);
        emails.add(emailUserTwo);
        FriendManagementConnectInput input = new FriendManagementConnectInput(emails);
        assertThatThrownBy(() -> this.friendsManagementRetrieveCommonFriendsService.getCommonFriends(input))
                .isInstanceOf(RecordNotFoundException.class);

      }

      @Test
      public void commonFriends_missingEmail_success() {
        final String emailUserOne = "hoang@gmail.com";
        List<String> emails = new ArrayList<String>();
        emails.add(emailUserOne);
        FriendManagementConnectInput input = new FriendManagementConnectInput(emails);
        assertThatThrownBy(() -> this.friendsManagementRetrieveCommonFriendsService.getCommonFriends(input))
                .isInstanceOf(EmptyException.class);
      }

      @Test
      public void commonFriends_emptyFriends_success() {
        final Integer userOneId = 1;
        final Integer userTwoId = 2;
        final Integer userThreeId = 3;
        final String emailUserOne = "hoang@gmail.com";
        final String emailUserTwo = "do@gmail.com";
        List<String> emails = new ArrayList<String>();
        emails.add(emailUserOne);
        emails.add(emailUserTwo);
        List<Integer> userIdsFriendOfOne = new ArrayList<>();
        userIdsFriendOfOne.add(userTwoId);
        userIdsFriendOfOne.add(userThreeId);
        FriendManagementConnectInput input = new FriendManagementConnectInput(emails);
        when(friendsManagementRepository.getIdsByEmail(input.getFriends()))
                .thenReturn(userIdsFriendOfOne);
        when(friendsManagementRepository.getIdByEmail(emailUserOne)).thenReturn(userOneId);
        when(friendsManagementRepository.getIdByEmail(emailUserTwo)).thenReturn(userTwoId);
        when(friendsManagementRepository.getFriendIdsByUserId(userOneId))
                .thenReturn(Collections.emptyList());
        when(friendsManagementRepository.getFriendIdsByUserId(userTwoId))
                .thenReturn(Collections.emptyList());
        FriendsManagementListOutput actual =
                friendsManagementRetrieveCommonFriendsService.getCommonFriends(input);
        assertThat(actual).isNotNull();
        assertThat(actual.getCount()).isEqualTo(0);
        assertThat(actual.getSuccess()).isEqualTo(true);
      }
      @Test
      public void commonFriends_emptyCommonFriends_success() {
        final Integer userOneId = 1;
        final Integer userTwoId = 2;
        final Integer userThreeId = 3;
        final String emailUserOne = "hoang@gmail.com";
        final String emailUserTwo = "do@gmail.com";
        List<String> emails = new ArrayList<String>();
        emails.add(emailUserOne);
        emails.add(emailUserTwo);
        List<Integer> userIdsFriendOfOne = new ArrayList<>();
        userIdsFriendOfOne.add(userTwoId);
        userIdsFriendOfOne.add(userThreeId);
        List<Integer> lstOne = new ArrayList<>();
        lstOne.add(userTwoId);
        List<Integer> lstTwo = new ArrayList<>();
        lstTwo.add(userThreeId);
        FriendManagementConnectInput input = new FriendManagementConnectInput(emails);
        when(friendsManagementRepository.getIdsByEmail(input.getFriends()))
                .thenReturn(userIdsFriendOfOne);
        when(friendsManagementRepository.getIdByEmail(emailUserOne)).thenReturn(userOneId);
        when(friendsManagementRepository.getIdByEmail(emailUserTwo)).thenReturn(userTwoId);
        when(friendsManagementRepository.getFriendIdsByUserId(userOneId))
                .thenReturn(lstOne);
        when(friendsManagementRepository.getFriendIdsByUserId(userTwoId))
                .thenReturn(lstTwo);
        FriendsManagementListOutput actual =
                friendsManagementRetrieveCommonFriendsService.getCommonFriends(input);
        assertThat(actual).isNotNull();
        assertThat(actual.getCount()).isEqualTo(0);
        assertThat(actual.getSuccess()).isEqualTo(true);
      }
}
