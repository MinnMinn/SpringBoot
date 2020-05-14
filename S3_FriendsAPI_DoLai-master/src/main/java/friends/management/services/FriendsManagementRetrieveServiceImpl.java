package friends.management.services;

import friends.management.domain.model.User;
import friends.management.domain.repositoryinterface.FriendsManagementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FriendsManagementRetrieveServiceImpl
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendsManagementRetrieveServiceImpl implements FriendsManagementRetrieveService {

    private final FriendsManagementRepository friendsManagementRepository;

    /**
     * handle
     *
     * @return ResponseEntity<Map<String, Object>>
     */
    public ResponseEntity<Map<String, Object>> handle(){
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("users" ,friendsManagementRepository.getListFriends());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
