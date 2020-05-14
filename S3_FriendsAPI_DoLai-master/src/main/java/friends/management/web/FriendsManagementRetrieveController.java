package friends.management.web;

import friends.management.services.FriendsManagementRetrieveService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

/**
 * FriendsManagementRetrieveController
 */
@Validated
@RequestMapping("friends-management")
@RestController
@RequiredArgsConstructor
public class FriendsManagementRetrieveController {

    private final FriendsManagementRetrieveService friendsManagementRetrieveService;
    private final Logger logger = LoggerFactory.getLogger(FriendsManagementRetrieveController.class);

    /**
     * getFriends
     * @return ResponseEntity<Map<String, Object>>
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> handle(){
        Map<String, Object> result = new HashMap<String, Object>();
        try{
            return friendsManagementRetrieveService.handle();
        }catch (RuntimeException e){
            logger.error("error" , e);
            result.put("error", "cannot make connection to database");
            return new ResponseEntity(result , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
