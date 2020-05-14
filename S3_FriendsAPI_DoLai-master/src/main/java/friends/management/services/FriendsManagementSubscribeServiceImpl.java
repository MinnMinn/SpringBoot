package friends.management.services;

import friends.management.domain.repositoryinterface.FriendsManagementRepository;
import friends.management.exception.AcceptableException;
import friends.management.exception.ConflictException;
import friends.management.exception.EmptyException;
import friends.management.exception.RecordNotFoundException;
import friends.management.response.FriendsManagementSuccessOutput;
import friends.management.web.SubscribeUserRequestForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class FriendsManagementSubscribeServiceImpl implements FriendsManagementSubscribeService {
    private final FriendsManagementRepository friendsManagementRepository;

    /**
     * subscribe
     * @param input SubscribeUserRequestForm
     * @return FriendsManagementSuccessOutput
     */
    @Override
    public FriendsManagementSuccessOutput subscribe(SubscribeUserRequestForm input) {
        Map<String, Object> result = new HashMap<>();

        List<String> emails = new ArrayList<>();
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
        Collections.sort(new ArrayList<>(userIds));
        // check relationship
        Integer isRelationship = friendsManagementRepository.isRelationship(userIds.get(0),userIds.get(1));
        if(isRelationship != null){
            // check block
            List<Integer> isBlock = friendsManagementRepository.isBlock(userIds.get(0), userIds.get(1));

            if(!isBlock.isEmpty()){
                throw new AcceptableException(input.getTarget() +" blocked "+ input.getRequestor());
            }
            //check subscribe
            Integer isSubscribe = friendsManagementRepository.isSubscribe(userIds.get(0), userIds.get(1));
            if(isSubscribe !=null){
                throw new ConflictException("subscribe already");
            }
            //update
            Boolean isSubscribeUpdated = friendsManagementRepository.subscribeUpdate(userIds.get(0),userIds.get(1));
            return new FriendsManagementSuccessOutput(isSubscribeUpdated);

        }
        // create
        Boolean isSubscribeCreated =friendsManagementRepository.subscribeCreate(userIds.get(0),userIds.get(1));
        return new FriendsManagementSuccessOutput(isSubscribeCreated);
    }
}
