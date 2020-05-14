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
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class FriendsManagementRetrieveFriendsServiceTest {
    @Mock
    private FriendsManagementRepository friendsManagementRepository;

    @InjectMocks
    private FriendsManagementRetrieveFriendsServiceImpl service;

    @Test
    public void retrieveFriends_success(){
        final Integer userOneId = 1;
        final Integer userTwoId = 2;
        final Integer userThreeId = 3;
        final String emailUserOne = "hoang@gmail.com";
        final String friendCommon = "common@gmail.com";
        final String emailUserTwo = "do@gmail.com";
        FriendManagementRetrieveFriendsInput input =
                new FriendManagementRetrieveFriendsInput(emailUserOne);
        when(friendsManagementRepository.getIdByEmail(emailUserOne)).thenReturn(userOneId);

        List<Integer> userIdsFriendOfOne = new ArrayList<>();
        userIdsFriendOfOne.add(userTwoId);
        userIdsFriendOfOne.add(userThreeId);
        when(friendsManagementRepository.getFriendIdsByUserId(userOneId)).thenReturn(userIdsFriendOfOne);

        List<String> emailResult = new ArrayList<String>();
        emailResult.add(emailUserTwo);
        emailResult.add(friendCommon);
        when(friendsManagementRepository.getFriendListByUserId(userIdsFriendOfOne)).thenReturn(emailResult);

        FriendsManagementListOutput actual = service.getFriends(input);
        assertThat(actual).isNotNull();
        assertThat(actual.getCount()).isEqualTo(emailResult.size());
        assertThat(actual.getSuccess()).isEqualTo(true);
    }

    @Test
    public void retrieveFriends_listEmpty_success(){
        final Integer userOneId = 1;
        final Integer userTwoId = 2;
        final Integer userThreeId = 3;
        final String emailUserOne = "hoang@gmail.com";
        FriendManagementRetrieveFriendsInput input =
                new FriendManagementRetrieveFriendsInput(emailUserOne);
        when(friendsManagementRepository.getIdByEmail(emailUserOne)).thenReturn(userOneId);
        List<Integer> userIdsFriendOfOne = new ArrayList<>();
        userIdsFriendOfOne.add(userTwoId);
        userIdsFriendOfOne.add(userThreeId);
        when(friendsManagementRepository.getFriendIdsByUserId(userOneId)).thenReturn(Collections.emptyList());
        FriendsManagementListOutput actual = service.getFriends(input);
        assertThat(actual).isNotNull();
        assertThat(actual.getCount()).isEqualTo(0);
        assertThat(actual.getSuccess()).isEqualTo(true);
    }

    @Test
    public void retrieveFriends_emailNotExist_Failed404() {
        final String emailUserOne = "hoang@gmail.com";
        FriendManagementRetrieveFriendsInput input =
                new FriendManagementRetrieveFriendsInput(emailUserOne);
        when(friendsManagementRepository.getIdByEmail(emailUserOne)).thenReturn(null);
        assertThatThrownBy(() -> this.service.getFriends(input))
                .isInstanceOf(RecordNotFoundException.class);
    }
}
