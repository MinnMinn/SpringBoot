package friends.management.data;

import friends.management.domain.repositoryinterface.FriendsManagementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * MyBatisFriendsManagement
 */
@Repository
@Transactional(propagation = Propagation.SUPPORTS)
@RequiredArgsConstructor
public class MyBatisFriendsManagement implements FriendsManagementRepository {

    private final FriendsManagementMapper friendsManagementMapper;

    /**
     * getListFriends
     * @return List<String>
     */
    @Override
    public List<String> getListFriends(){
        return friendsManagementMapper.query();
    }

    /**
     * getIdsByEmail
     * @return List<Integer>
     */
    @Override
    public List<Integer> getIdsByEmail(List<String> emails) {
        return friendsManagementMapper.getIdsByEmailMapper(emails);
    }

    /**
     * isBlock
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return List<Integer>
     */
    @Override
    public List<Integer> isBlock(Integer userOneId, Integer userTwoId) {
        return friendsManagementMapper.isBlockMapper(userOneId,userTwoId);
    }

    /**
     * connect
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return Boolean
     */
    @Override
    public Boolean connect(Integer userOneId, Integer userTwoId) {
        return friendsManagementMapper.connectMapper(userOneId, userTwoId);
    }

    /**
     * getIdByEmail
     * @param email String
     * @return Integer
     */
    @Override
    public Integer getIdByEmail(String email) {
       return friendsManagementMapper.getIdByEmailMapper(email);
    }

    /**
     * getFriendIdsByUserId
     * @param userId Integer
     * @return List<Integer>
     */
    @Override
    public List<Integer> getFriendIdsByUserId(Integer userId) {
        List<Integer> friendIDs = new ArrayList<Integer>();
        List<Integer> lst1 = friendsManagementMapper.getFriendIdsByUserOneIdMapper(userId);
        List<Integer> lst2 = friendsManagementMapper.getFriendIdsByUserTwoIdMapper(userId);
        friendIDs.addAll(lst1);
        friendIDs.addAll(lst2);
        return friendIDs;
    }

    /**
     * getFriendListByUserId
     * @param userIds List<Integer>
     * @return List<String>
     */
    @Override
    public List<String> getFriendListByUserId(List<Integer> userIds) {
        return friendsManagementMapper.getFriendListByUserIdMapper(userIds);
    }

    /**
     * isRelationship
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return Integer
     */
    @Override
    public Integer isRelationship(Integer userOneId, Integer userTwoId) {
        return friendsManagementMapper.isRelationshipMapper(userOneId, userTwoId);
    }

    /**
     * blockFriendUpdate
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return Boolean
     */
    @Override
    public Boolean blockFriendUpdate(Integer userOneId, Integer userTwoId) {
        return friendsManagementMapper.blockFriendUpdateMapper(userOneId,userTwoId);
    }

    /**
     * blockFriendCreate
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return Boolean
     */
    @Override
    public Boolean blockFriendCreate(Integer userOneId, Integer userTwoId) {
        return friendsManagementMapper.blockFriendCreateMapper(userOneId,userTwoId);
    }

    /**
     * subscribeUpdate
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return Boolean
     */
    @Override
    public Boolean subscribeUpdate(Integer userOneId, Integer userTwoId) {
        return friendsManagementMapper.subscribeUpdateMapper(userOneId,userTwoId);
    }

    /**
     * subscribeCreate
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return Boolean
     */
    @Override
    public Boolean subscribeCreate(Integer userOneId, Integer userTwoId) {
        return friendsManagementMapper.subscribeCreateMapper(userOneId,userTwoId);
    }

    /**
     * getFriendIdsHasUpdateByUserId
     * @param userId Integer
     * @return List<Integer>
     */
    @Override
    public List<Integer> getFriendIdsHasUpdateByUserId(Integer userId) {
        List<Integer> friendIDsHasUpdate = new ArrayList<Integer>();
        List<Integer> lst1 = friendsManagementMapper.getFriendIdsHasUpdateByUserOneIdMapper(userId);
        List<Integer> lst2 = friendsManagementMapper.getFriendIdsHasUpdateByUserTwoIdMapper(userId);
        friendIDsHasUpdate.addAll(lst1);
        friendIDsHasUpdate.addAll(lst2);
        return friendIDsHasUpdate;
    }

    /**
     * isFriend
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return Integer
     */
    @Override
    public Integer isFriend(Integer userOneId, Integer userTwoId) {
        return friendsManagementMapper.isFriendMapper(userOneId,userTwoId);
    }

    /**
     * isSubscribe
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return Integer
     */
    @Override
    public Integer isSubscribe(Integer userOneId, Integer userTwoId) {
        return friendsManagementMapper.isSubscribeMapper(userOneId,userTwoId);
    }

    /**
     * isBlockUpdateUserOne
     * @param userOneId Integer
     * @param userIdsMention List<Integer>
     * @return List<Integer>
     */
    @Override
    public List<Integer> isBlockUpdateUserOne(Integer userOneId, List<Integer> userIdsMention) {
        return friendsManagementMapper.isBlockUpdateUserOneMapper(userOneId,userIdsMention);
    }

    /**
     * isBlockUpdateUserTwo
     * @param userTwoId Integer
     * @param userIdsMention List<Integer>
     * @return List<Integer>
     */
    @Override
    public List<Integer> isBlockUpdateUserTwo(Integer userTwoId, List<Integer> userIdsMention) {
        return friendsManagementMapper.isBlockUpdateUserTwoMapper(userTwoId,userIdsMention);
    }

}
