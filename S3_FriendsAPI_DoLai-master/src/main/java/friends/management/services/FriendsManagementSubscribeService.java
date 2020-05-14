package friends.management.services;

import friends.management.response.FriendsManagementSuccessOutput;
import friends.management.web.SubscribeUserRequestForm;

public interface FriendsManagementSubscribeService {
    FriendsManagementSuccessOutput subscribe(SubscribeUserRequestForm input);
}
