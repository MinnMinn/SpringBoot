package friends.management.services;

import friends.management.response.FriendsManagementListOutput;

import java.util.Optional;

/**
 * FriendsManagementRetrieveFriendsService
 */
public interface FriendsManagementRetrieveFriendsService {
    /**
     * getFriends
     * @param email FriendManagementRetrieveFriendsInput
     * @return ResponseEntity<Map<String, Object>>
     */
    FriendsManagementListOutput getFriends(FriendManagementRetrieveFriendsInput email);
}
