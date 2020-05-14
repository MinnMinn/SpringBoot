package friends.management.services;

import friends.management.domain.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * FriendsManagementRetrieveService
 */
public interface FriendsManagementRetrieveService {
    /**
     * handle
     * @return ResponseEntity<Map<String, Object>>
     */
    ResponseEntity<Map<String, Object>> handle();
}
