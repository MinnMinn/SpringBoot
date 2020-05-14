package friends.management.services;

import friends.management.response.FriendsManagementListOutput;
import org.springframework.http.ResponseEntity;
import java.util.Map;

/**
 * FriendsManagementRetrieveCommonFriendsService
 */
public interface FriendsManagementRetrieveCommonFriendsService {
    /**
     * getCommonFriends
     * @param input FriendManagementConnectInput
     * @return ResponseEntity<Map<String, Object>>
     */
    FriendsManagementListOutput getCommonFriends(FriendManagementConnectInput input);
}
