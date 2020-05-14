package friends.management.services;

import friends.management.response.FriendsManagementReceiveOutput;
import friends.management.web.ReceiveListRequestForm;

/**
 * FriendsManagementReceiveListService
 */
public interface FriendsManagementReceiveListService {
    FriendsManagementReceiveOutput getReceiveList(ReceiveListRequestForm input);
}
