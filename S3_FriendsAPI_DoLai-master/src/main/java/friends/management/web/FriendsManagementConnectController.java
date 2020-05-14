package friends.management.web;

import friends.management.exception.AcceptableException;
import friends.management.exception.ConflictException;
import friends.management.exception.EmptyException;
import friends.management.exception.RecordNotFoundException;
import friends.management.response.FriendsManagementSuccessOutput;
import friends.management.services.FriendManagementConnectInput;
import friends.management.services.FriendsManagementConnectService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * FriendsManagementConnectController
 */
@RestController
@RequestMapping("friends-management/connect")
@RequiredArgsConstructor
public class FriendsManagementConnectController {

    private final FriendsManagementConnectService friendsManagementConnectService;
    private final Logger logger = LoggerFactory.getLogger(FriendsManagementConnectController.class);

    /**
     * handle
     * @param userFriendsRequestForm UserFriendsRequestForm
     * @return ResponseEntity<FriendsManagementSuccessOutput>
     */
    @PostMapping
    public ResponseEntity<FriendsManagementSuccessOutput> handle(
        @RequestBody @NonNull final UserFriendsRequestForm userFriendsRequestForm
    ){
        final FriendManagementConnectInput input = userFriendsRequestForm.toInput();
        try{
            FriendsManagementSuccessOutput result = friendsManagementConnectService.connect(input);
            return ResponseEntity.ok().body(result);
        }catch (EmptyException e){
            throw new EmptyException(e.getMessage());
        }catch (RecordNotFoundException e){
            throw  new RecordNotFoundException(e.getMessage());
        }catch (AcceptableException e){
            throw new AcceptableException(e.getMessage());
        }catch (ConflictException e){
            throw  new ConflictException(e.getMessage());
        }catch (RuntimeException e){
            logger.error("friends-management app :" , e);
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("error_message", "internal server error");
            body.put("error_code", 500);
            return new ResponseEntity(body , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
