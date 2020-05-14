package friends.management.services;

import friends.management.domain.repositoryinterface.FriendsManagementRepository;
import friends.management.exception.EmptyException;
import friends.management.exception.RecordNotFoundException;
import friends.management.response.FriendsManagementListOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.Collections.emptyList;

/**
 * FriendsManagementRetrieveFriendsServiceImpl
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendsManagementRetrieveFriendsServiceImpl implements FriendsManagementRetrieveFriendsService {

    private final FriendsManagementRepository friendsManagementRepository;

    /**
     * getFriends
     * @param email FriendManagementRetrieveFriendsInput
     * @return FriendsManagementListOutput
     */
    @Override
    public FriendsManagementListOutput getFriends(FriendManagementRetrieveFriendsInput email) {
        Map<String, Object> result = new HashMap<>();
       //get Id by email
        Integer userId = friendsManagementRepository.getIdByEmail(email.getEmail());
        if(userId == null){
            throw new RecordNotFoundException("Not found email "+email.getEmail());
        }
        List<Integer> friendIds = friendsManagementRepository.getFriendIdsByUserId(userId);
        if(friendIds.size() == 0){
            return FriendsManagementListOutputConverter.from(Collections.emptyList());
        }
        List<String> users = friendsManagementRepository.getFriendListByUserId(friendIds);
        return FriendsManagementListOutputConverter.from(users);

    }
}
