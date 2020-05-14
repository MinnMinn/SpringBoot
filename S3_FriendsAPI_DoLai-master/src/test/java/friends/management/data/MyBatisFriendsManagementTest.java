package friends.management.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class MyBatisFriendsManagementTest {

    @Mock
    private FriendsManagementMapper friendsManagementMapper;

    @InjectMocks
    private MyBatisFriendsManagement myBatisFriendsManagement;

    @Test
    public void getListFriends_success(){
        String emailUserOne = "test@gmail.com";
        String emailUserTwo = "testTwo@gmail.com";
        List<String> emails = new ArrayList<String>();
        emails.add(emailUserOne);
        emails.add(emailUserTwo);
        when(friendsManagementMapper.query()).thenReturn(emails);
        final List<String> actual = myBatisFriendsManagement.getListFriends();
        assertThat(actual.size()).isEqualTo(2);
        verify(this.friendsManagementMapper, times(1)).query();
    }

    @Test
    public void getIdsByEmail_success(){
        String emailUserOne = "test@gmail.com";
        String emailUserTwo = "testTwo@gmail.com";
        List<String> emails = new ArrayList<String>();
        emails.add(emailUserOne);
        emails.add(emailUserTwo);
        List<Integer> userIds = new ArrayList<Integer>();
        userIds.add(1);
        userIds.add(2);
        when(friendsManagementMapper.getIdsByEmailMapper(emails)).thenReturn(userIds);
        final List<Integer> actual = myBatisFriendsManagement.getIdsByEmail(emails);
        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual.get(0)).isEqualTo(1);
        verify(this.friendsManagementMapper, times(1)).getIdsByEmailMapper(emails);
    }

    @Test
    public void isBlock_success(){
        Integer userOneId = 1;
        Integer userTwoId = 2;
        List<Integer> userIds = new ArrayList<Integer>();
        userIds.add(userOneId);
        userIds.add(userTwoId);
        when(friendsManagementMapper.isBlockMapper(userOneId, userTwoId)).thenReturn(userIds);
        final List<Integer> actual = myBatisFriendsManagement.isBlock(userOneId,userTwoId);
        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual.get(0)).isEqualTo(userOneId);
        verify(this.friendsManagementMapper, times(1)).isBlockMapper(userOneId,userTwoId);
    }

    @Test
    public void connect_success(){
        Integer userOneId = 1;
        Integer userTwoId = 2;
        when(friendsManagementMapper.connectMapper(userOneId, userTwoId)).thenReturn(true);
        final Boolean actual = myBatisFriendsManagement.connect(userOneId,userTwoId);
        assertThat(actual).isEqualTo(true);
    }

    @Test
    public void getIdByEmail_success(){
        String emailUserOne = "test@gmail.com";
        Integer userOneId = 1;
        when(friendsManagementMapper.getIdByEmailMapper(emailUserOne)).thenReturn(userOneId);
        final Integer actual = myBatisFriendsManagement.getIdByEmail(emailUserOne);
        assertThat(actual).isEqualTo(userOneId);
    }

    @Test
    public void getFriendListByUserId_success(){
        Integer userOneId = 1;
        Integer userTwoId = 2;
        List<Integer> userIds = new ArrayList<Integer>();
        userIds.add(userOneId);
        userIds.add(userTwoId);
        String emailUserOne = "test@gmail.com";
        String emailUserTwo = "testTwo@gmail.com";
        List<String> emails = new ArrayList<String>();
        emails.add(emailUserOne);
        emails.add(emailUserTwo);
        when(friendsManagementMapper.getFriendListByUserIdMapper(userIds)).thenReturn(emails);
        final List<String> actual = myBatisFriendsManagement.getFriendListByUserId(userIds);
        assertThat(actual).isEqualTo(emails);
    }



    @Test
    public void getFriendIdsByUserId_success(){
        Integer userOneId = 1;
        Integer userTwoId = 2;
        Integer userThreeId = 3;
        List<Integer> userIds = new ArrayList<Integer>();
        userIds.add(userTwoId);
        List<Integer> userIdsTwo = new ArrayList<Integer>();
        userIdsTwo.add(userThreeId);
        when(friendsManagementMapper.getFriendIdsByUserOneIdMapper(userOneId)).thenReturn(userIds);
        when(friendsManagementMapper.getFriendIdsByUserTwoIdMapper(userOneId)).thenReturn(userIdsTwo);
        final List<Integer> actual = myBatisFriendsManagement.getFriendIdsByUserId(userOneId);
        assertThat(actual.size()).isEqualTo(2);
    }

    @Test
    public void isRelationship_success(){
        Integer userOneId = 1;
        Integer userTwoId = 2;
        when(friendsManagementMapper.isRelationshipMapper(userOneId, userTwoId)).thenReturn(userOneId);
        final Integer actual = myBatisFriendsManagement.isRelationship(userOneId,userTwoId);
        assertThat(actual).isEqualTo(userOneId);
    }


    @Test
    public void blockFriendCreate_success(){
        Integer userOneId = 1;
        Integer userTwoId = 2;
        when(friendsManagementMapper.blockFriendCreateMapper(userOneId, userTwoId)).thenReturn(true);
        final Boolean actual = myBatisFriendsManagement.blockFriendCreate(userOneId,userTwoId);
        assertThat(actual).isEqualTo(true);
    }
    @Test
    public void blockFriendUpdate_success(){
        Integer userOneId = 1;
        Integer userTwoId = 2;
        when(friendsManagementMapper.blockFriendUpdateMapper(userOneId, userTwoId)).thenReturn(true);
        final Boolean actual = myBatisFriendsManagement.blockFriendUpdate(userOneId,userTwoId);
        assertThat(actual).isEqualTo(true);
    }
    @Test
    public void subscribeUpdate_success(){
        Integer userOneId = 1;
        Integer userTwoId = 2;
        when(friendsManagementMapper.subscribeUpdateMapper(userOneId, userTwoId)).thenReturn(true);
        final Boolean actual = myBatisFriendsManagement.subscribeUpdate(userOneId,userTwoId);
        assertThat(actual).isEqualTo(true);
    }
    @Test
    public void subscribeCreate_success(){
        Integer userOneId = 1;
        Integer userTwoId = 2;
        when(friendsManagementMapper.subscribeCreateMapper(userOneId, userTwoId)).thenReturn(true);
        final Boolean actual = myBatisFriendsManagement.subscribeCreate(userOneId,userTwoId);
        assertThat(actual).isEqualTo(true);
    }

    @Test
    public void getFriendIdsHasUpdateByUserId_success(){
        Integer userOneId = 1;
        Integer userTwoId = 2;
        Integer userThreeId = 3;
        List<Integer> userIds = new ArrayList<Integer>();
        userIds.add(userTwoId);
        List<Integer> userIdsTwo = new ArrayList<Integer>();
        userIdsTwo.add(userThreeId);
        when(friendsManagementMapper.getFriendIdsHasUpdateByUserOneIdMapper(userOneId)).thenReturn(userIds);
        when(friendsManagementMapper.getFriendIdsHasUpdateByUserTwoIdMapper(userOneId)).thenReturn(userIdsTwo);
        final List<Integer> actual = myBatisFriendsManagement.getFriendIdsHasUpdateByUserId(userOneId);
        assertThat(actual.size()).isEqualTo(2);
    }
}
