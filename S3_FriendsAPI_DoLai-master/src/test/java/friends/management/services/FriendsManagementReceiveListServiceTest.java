package friends.management.services;

import friends.management.domain.repositoryinterface.FriendsManagementRepository;
import friends.management.exception.EmptyException;
import friends.management.exception.RecordNotFoundException;
import friends.management.response.FriendsManagementReceiveOutput;
import friends.management.web.ReceiveListRequestForm;
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
public class FriendsManagementReceiveListServiceTest {
    @Mock
    private FriendsManagementRepository friendsManagementRepository;

    @InjectMocks
    private FriendsManagementReceiveListServiceImpl service;

    @Test
    public void receiveList_success(){
        final Integer userOneId = 1;
        final Integer userTwoId = 2;
        final Integer userThreeId = 3;
        final String emailUserOne = "hoang@gmail.com";
        final String friendCommon = "common@gmail.com";
        final String emailUserTwo = "do@gmail.com";
        ReceiveListRequestForm input =
                new ReceiveListRequestForm();
        input.setSender(emailUserOne);
        input.setText("Test Receive list ");
        when(friendsManagementRepository.getIdByEmail(emailUserOne)).thenReturn(userOneId);

        List<Integer> userIdsFriendOfOne = new ArrayList<>();
        userIdsFriendOfOne.add(userTwoId);
        userIdsFriendOfOne.add(userThreeId);
        when(friendsManagementRepository.getFriendIdsHasUpdateByUserId(userOneId)).thenReturn(userIdsFriendOfOne);

        List<String> emailResult = new ArrayList<String>();
        emailResult.add(emailUserTwo);
        emailResult.add(friendCommon);
        when(friendsManagementRepository.getFriendListByUserId(userIdsFriendOfOne)).thenReturn(emailResult);

        FriendsManagementReceiveOutput actual = service.getReceiveList(input);
        Map<String, Object> log = new HashMap<>();
        log.put("success" ,true);
        log.put("recipients" ,emailResult);
        assertThat(actual).isNotNull();
        assertThat(actual.getRecipients()).isEqualTo(emailResult);
        assertThat(actual.getSuccess()).isEqualTo(true);
    }

    @Test
    public void receiveList_listEmpty_success(){
        final Integer userOneId = 1;
        final Integer userTwoId = 2;
        final Integer userThreeId = 3;
        final String emailUserOne = "hoang@gmail.com";
        ReceiveListRequestForm input =
                new ReceiveListRequestForm();
        input.setSender(emailUserOne);
        input.setText("Test Receive list ");
        when(friendsManagementRepository.getIdByEmail(emailUserOne)).thenReturn(userOneId);
        List<Integer> userIdsFriendOfOne = new ArrayList<>();
        userIdsFriendOfOne.add(userTwoId);
        userIdsFriendOfOne.add(userThreeId);

        when(friendsManagementRepository.getFriendIdsByUserId(userOneId)).thenReturn(userIdsFriendOfOne);

        when(friendsManagementRepository.getFriendListByUserId(userIdsFriendOfOne)).thenReturn(Collections.emptyList());

        FriendsManagementReceiveOutput actual = service.getReceiveList(input);
        assertThat(actual).isNotNull();
        assertThat(actual.getRecipients()).isEqualTo(Collections.emptyList());
        assertThat(actual.getSuccess()).isEqualTo(true);
    }

    @Test
    public void receiveList_emailNotExist_Failed404() {
        final String emailUserOne = "hoang@gmail.com";
        ReceiveListRequestForm input =
                new ReceiveListRequestForm();
        input.setSender(emailUserOne);
        input.setText("Test Receive list ");
        when(friendsManagementRepository.getIdByEmail(emailUserOne)).thenReturn(null);
        assertThatThrownBy(() -> this.service.getReceiveList(input))
                .isInstanceOf(RecordNotFoundException.class);
    }

    @Test
    public void receiveList_hasEmailMention_success(){
        final Integer userOneId = 1;
        final Integer userTwoId = 2;
        final Integer userThreeId = 3;
        final String emailUserOne = "hoang@gmail.com";
        final String friendCommon = "common@gmail.com";
        final String emailUserTwo = "do@gmail.com";
        ReceiveListRequestForm input =
                new ReceiveListRequestForm();
        input.setSender(emailUserOne);
        input.setText("Test Receive test@gmail.com ");
        List<String> emailMentionTest = new ArrayList<>();
        emailMentionTest.add("test@gmail.com");
        List<Integer> idsMention = new ArrayList<>();
        idsMention.add(4);
        when(friendsManagementRepository.getIdByEmail(emailUserOne)).thenReturn(userOneId);
        when(friendsManagementRepository.getIdsByEmail(emailMentionTest)).thenReturn(idsMention);
        when(friendsManagementRepository.isBlockUpdateUserOne(userOneId,idsMention)).thenReturn(idsMention);
        when(friendsManagementRepository.isBlockUpdateUserTwo(userOneId,idsMention)).thenReturn(idsMention);
        when(friendsManagementRepository.getFriendListByUserId(idsMention)).thenReturn(emailMentionTest);
        List<Integer> userIdsFriendOfOne = new ArrayList<>();
        userIdsFriendOfOne.add(userTwoId);
        userIdsFriendOfOne.add(userThreeId);
        when(friendsManagementRepository.getFriendIdsHasUpdateByUserId(userOneId)).thenReturn(userIdsFriendOfOne);

        List<String> emailResult = new ArrayList<String>();
        emailResult.add(emailUserTwo);
        emailResult.add(friendCommon);
        when(friendsManagementRepository.getFriendListByUserId(userIdsFriendOfOne)).thenReturn(emailResult);

        FriendsManagementReceiveOutput actual = service.getReceiveList(input);
        Map<String, Object> log = new HashMap<>();
        log.put("success" ,true);
        log.put("recipients" ,emailResult);
        assertThat(actual).isNotNull();
        assertThat(actual.getRecipients()).isEqualTo(emailResult);
        assertThat(actual.getSuccess()).isEqualTo(true);
    }

}
