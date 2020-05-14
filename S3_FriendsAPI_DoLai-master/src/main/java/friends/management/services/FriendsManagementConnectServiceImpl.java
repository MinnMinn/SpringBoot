package friends.management.services;

import friends.management.domain.repositoryinterface.FriendsManagementRepository;
import friends.management.exception.AcceptableException;
import friends.management.exception.ConflictException;
import friends.management.exception.EmptyException;
import friends.management.exception.RecordNotFoundException;
import friends.management.response.FriendsManagementSuccessOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.*;

/**
 * FriendsManagementConnectServiceImpl
 */
@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class FriendsManagementConnectServiceImpl implements FriendsManagementConnectService{


    private final FriendsManagementRepository friendsManagementRepository;

    /**
     * connect
     * @param input FriendManagementConnectInput
     * @return FriendsManagementSuccessOutput
     */
    @Override
    public FriendsManagementSuccessOutput connect(FriendManagementConnectInput input) {
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
        //sort increase
        Collections.sort(new ArrayList<>(ids));
        Integer userOneId = ids.get(0);
        Integer userTwoId = ids.get(1);
        //check isFriend
        Integer isFriend = friendsManagementRepository.isFriend(userOneId,userTwoId);
        if(isFriend != null){
            throw new ConflictException("friend already");
        }
        //check user has in block list
        List<Integer> isBlock = friendsManagementRepository.isBlock(userOneId,userTwoId);
        if(!isBlock.isEmpty()){
            throw new AcceptableException("Friend has been blocked");
        }
        //check user
        Boolean connect = friendsManagementRepository.connect(userOneId,userTwoId);
       return new FriendsManagementSuccessOutput(true);
    }
}
