package friends.management.services;

import friends.management.response.FriendsManagementSuccessOutput;
import friends.management.web.BlockUserRequestForm;

/**
 * FriendsManagementBlockFriendService
 */
public interface FriendsManagementBlockFriendService {
    FriendsManagementSuccessOutput blockFriend(BlockUserRequestForm input);
}
