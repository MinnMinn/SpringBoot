package friends.management.services;

import friends.management.response.FriendsManagementSuccessOutput;
import org.springframework.http.ResponseEntity;
import java.util.Map;

/**
 * FriendsManagementConnectService
 */
public interface FriendsManagementConnectService {
    /**
     * connect
     * @param input FriendManagementConnectInput
     * @return FriendsManagementSuccessOutput
     */
    FriendsManagementSuccessOutput connect(FriendManagementConnectInput input);
}
