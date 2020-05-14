package friends.management.web;

import friends.management.exception.EmptyException;
import friends.management.exception.RecordNotFoundException;
import friends.management.response.FriendsManagementListOutput;
import friends.management.services.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * FriendsManagementRetrieveCommonFriendsController
 */
@Validated
@RequestMapping("friends-management/common")
@RestController
@RequiredArgsConstructor
public class FriendsManagementRetrieveCommonFriendsController {

    private final FriendsManagementRetrieveCommonFriendsService friendsManagementRetrieveCommonFriendsService;

    private final Logger logger = LoggerFactory.getLogger(FriendsManagementRetrieveCommonFriendsController.class);

    /**
     * handle
     * @param userFriendsRequestForm InputForm
     * @return ResponseEntity<FriendsManagementListOutput>
     */
    @GetMapping
    public ResponseEntity<FriendsManagementListOutput> handle(
            @RequestBody @Validated @NonNull final UserFriendsRequestForm userFriendsRequestForm
    ){
        final FriendManagementConnectInput input = userFriendsRequestForm.toInput();
        try{
            FriendsManagementListOutput result = friendsManagementRetrieveCommonFriendsService.getCommonFriends(input);
            return ResponseEntity.ok().body(result);
        }catch (EmptyException e){
            throw new EmptyException(e.getMessage());
        }catch (RecordNotFoundException e){
            throw  new RecordNotFoundException(e.getMessage());
        }catch (RuntimeException e){
            logger.error("friends-management app :" , e);
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("error_message", "internal server error");
            body.put("error_code", 500);
            return new ResponseEntity(body , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
