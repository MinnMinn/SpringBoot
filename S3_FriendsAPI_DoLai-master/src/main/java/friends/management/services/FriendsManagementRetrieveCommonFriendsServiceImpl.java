package friends.management.services;

import friends.management.domain.repositoryinterface.FriendsManagementRepository;
import friends.management.exception.EmptyException;
import friends.management.exception.RecordNotFoundException;
import friends.management.response.FriendsManagementListOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.*;
import static java.util.Collections.emptyList;

/**
 * FriendsManagementRetrieveCommonFriendsServiceImpl
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendsManagementRetrieveCommonFriendsServiceImpl implements
        FriendsManagementRetrieveCommonFriendsService {

    private final FriendsManagementRepository friendsManagementRepository;

    /**
     * getCommonFriends
     * @param input FriendManagementConnectInput
     * @return FriendsManagementListOutput
     */
    @Override
    public FriendsManagementListOutput getCommonFriends(FriendManagementConnectInput input) {
        // check input empty
        if (CollectionUtils.isEmpty(input.getFriends())) {
            throw new EmptyException("Friend list cannot be empty");
        }
        if (input.getFriends().size() == 1) {
            throw new EmptyException("Please input two emails valid");
        }
        // get list id of users by emails
        List<Integer> ids = friendsManagementRepository.getIdsByEmail(input.getFriends());
        if(ids.size() != 2){
            throw new RecordNotFoundException("Email does not exists");
        }
        // get user id by emails
        List<Integer> friendOfUserOne = getFriendsByUserId(input.getFriends().get(0));
        List<Integer> friendOfUserTwo = getFriendsByUserId(input.getFriends().get(1));

        if(friendOfUserOne.isEmpty() || friendOfUserTwo.isEmpty()){
            return FriendsManagementListOutputConverter.from(Collections.emptyList());
        }
        List<Integer> commonFriendId = new ArrayList<>();
        for (int x : friendOfUserOne) {
            for (int y : friendOfUserTwo) {
                if (x == y) {
                    commonFriendId.add(x);
                }
            }
        }
        if(commonFriendId.isEmpty()){
            return FriendsManagementListOutputConverter.from(Collections.emptyList());
        }
        List<String> commonFriendEmails = friendsManagementRepository.getFriendListByUserId(commonFriendId);
        return FriendsManagementListOutputConverter.from(commonFriendEmails);
    }

    /**
     * getFriendsByUserId
     * @param email String
     * @return List<Integer>
     */
    private List<Integer> getFriendsByUserId(String email){
        Integer userId = friendsManagementRepository.getIdByEmail(email);
        if(userId == null){
            return emptyList();
        }
        // get friends by user id
        return friendsManagementRepository.getFriendIdsByUserId(userId);
    }
}
