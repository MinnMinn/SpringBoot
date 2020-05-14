package friends.management.domain.repositoryinterface;

import java.util.List;

/**
 * FriendsManagementRepository
 */
public interface FriendsManagementRepository {
    /**
     * getListFriends
     * @return List<String>
     */
    List<String> getListFriends();

    /**
     * getIdsByEmail
     * @param  emails String
     * @return List<Integer>
     */
    List<Integer> getIdsByEmail(List<String> emails);

    /**
     * isBlock
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return List<Integer>
     */
    List<Integer> isBlock(Integer userOneId, Integer userTwoId);

    /**
     * connect
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return Boolean
     */
    Boolean connect(Integer userOneId, Integer userTwoId);

    /**
     * getIdByEmail
     * @param email String
     * @return Integer
     */
    Integer getIdByEmail(String email);

    /**
     * getFriendIdsByUserId
     * @param userId Integer
     * @return  List<Integer>
     */
    List<Integer> getFriendIdsByUserId(Integer userId);

    /**
     * getFriendListByUserId
     * @param userIds List<Integer>
     * @return List<String>
     */
    List<String> getFriendListByUserId(List<Integer> userIds);

    /**
     * isRelationship
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return Integer
     */
    Integer isRelationship(Integer userOneId, Integer userTwoId);

    /**
     * blockFriendUpdate
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return Boolean
     */
    Boolean blockFriendUpdate(Integer userOneId, Integer userTwoId);

    /**
     * blockFriendCreate
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return Boolean
     */
    Boolean blockFriendCreate(Integer userOneId, Integer userTwoId);
    /**
     * subscribeUpdate
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return Boolean
     */
    Boolean subscribeUpdate(Integer userOneId, Integer userTwoId);
    /**
     * subscribeCreate
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return Boolean
     */
    Boolean subscribeCreate(Integer userOneId, Integer userTwoId);

    /**
     * getFriendIdsHasUpdateByUserId
     * @param userId Integer
     * @return  List<Integer>
     */
    List<Integer> getFriendIdsHasUpdateByUserId(Integer userId);

    /**
     * isFriend
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return Integer
     */
    Integer isFriend(Integer userOneId, Integer userTwoId);

    /**
     * isSubscribe
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return Integer
     */
    Integer isSubscribe(Integer userOneId, Integer userTwoId);

    /**
     * isBlockUpdateUserOne
     * @param userOneId Integer
     * @param userIdsMention List<Integer>
     * @return List<Integer>
     */
    List<Integer> isBlockUpdateUserOne(Integer userOneId , List<Integer> userIdsMention);

    /**
     * isBlockUpdateUserOne
     * @param userTwoId Integer
     * @param userIdsMention List<Integer>
     * @return List<Integer>
     */
    List<Integer> isBlockUpdateUserTwo(Integer userTwoId , List<Integer> userIdsMention);

}
