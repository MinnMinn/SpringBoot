package friends.management.web;

import friends.management.exception.AcceptableException;
import friends.management.exception.ConflictException;
import friends.management.exception.EmptyException;
import friends.management.exception.RecordNotFoundException;
import friends.management.response.FriendsManagementSuccessOutput;
import friends.management.services.FriendsManagementBlockFriendService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Validated
@RequestMapping("friends-management/block")
@RestController
@RequiredArgsConstructor
public class FriendsManagementBlockFriendController {

    private final FriendsManagementBlockFriendService friendsManagementBlockFriendService;
    private final Logger logger = LoggerFactory.getLogger(FriendsManagementBlockFriendController.class);

    /**
     * handle
     * @param inputForm BlockUserRequestForm
     * @return ResponseEntity<FriendsManagementSuccessOutput>
     */
    @PostMapping
    public ResponseEntity<FriendsManagementSuccessOutput> handle(
            @RequestBody @NonNull final BlockUserRequestForm inputForm
    ){
        try{
            FriendsManagementSuccessOutput result = friendsManagementBlockFriendService.blockFriend(inputForm);
            return ResponseEntity.ok().body(result);
        }catch (EmptyException e){
            throw new EmptyException(e.getMessage());
        }catch (RecordNotFoundException e){
            throw  new RecordNotFoundException(e.getMessage());
        }catch (AcceptableException e){
            throw new AcceptableException(e.getMessage());
        }catch (RuntimeException e){
            logger.error("friends-management app :" , e);
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("error_message", "internal server error");
            body.put("error_code", 500);
            return new ResponseEntity(body , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
