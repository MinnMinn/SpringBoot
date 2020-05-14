package friends.management.services;

import friends.management.domain.repositoryinterface.FriendsManagementRepository;
import friends.management.exception.AcceptableException;
import friends.management.exception.EmptyException;
import friends.management.exception.RecordNotFoundException;
import friends.management.response.FriendsManagementSuccessOutput;
import friends.management.web.BlockUserRequestForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class FriendsManagementBlockFriendServiceImpl implements FriendsManagementBlockFriendService{

    private final FriendsManagementRepository friendsManagementRepository;

    @Override
    public FriendsManagementSuccessOutput blockFriend(BlockUserRequestForm input) {
        //check block
        List<String> emails = new ArrayList<String>();
        emails.add(input.getRequestor());
        emails.add(input.getTarget());
        List<Integer> userIds = friendsManagementRepository
                .getIdsByEmail(emails);
        if (emails.contains(null) || emails.contains("") ) {
            throw new EmptyException("Please input two emails valid");
        }
        if(userIds.size() != 2){
            throw new RecordNotFoundException("Email does not exists");
        }
        List<Integer> isBlock = friendsManagementRepository.isBlock(userIds.get(0), userIds.get(1));

        if(!isBlock.isEmpty()){
            throw new AcceptableException(input.getTarget() +" blocked "+ input.getRequestor());
        }
        Collections.sort(new ArrayList<>(userIds));
        // block
        Integer isRelationship = friendsManagementRepository.isRelationship(userIds.get(0),userIds.get(1));
        if(isRelationship != null){
            //update
            Boolean updateBlock = friendsManagementRepository.blockFriendUpdate(userIds.get(0),userIds.get(1));
            return new FriendsManagementSuccessOutput(true);
        }
        // create
        Boolean blocked =friendsManagementRepository.blockFriendCreate(userIds.get(0),userIds.get(1));
        return new FriendsManagementSuccessOutput(true);
    }
}
