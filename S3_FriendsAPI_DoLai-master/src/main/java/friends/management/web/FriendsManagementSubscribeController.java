package friends.management.web;

import friends.management.exception.AcceptableException;
import friends.management.exception.ConflictException;
import friends.management.exception.EmptyException;
import friends.management.exception.RecordNotFoundException;
import friends.management.response.FriendsManagementSuccessOutput;
import friends.management.services.FriendsManagementSubscribeService;
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
import java.util.LinkedHashMap;
import java.util.Map;

@RequestMapping("friends-management/subscribe")
@RestController
@RequiredArgsConstructor
public class FriendsManagementSubscribeController {
    private final FriendsManagementSubscribeService friendsManagementSubscribeService;

    private final Logger logger = LoggerFactory.getLogger(FriendsManagementSubscribeController.class);

    /**
     * handle
     * @param inputForm SubscribeUserRequestForm
     * @return  ResponseEntity<FriendsManagementSuccessOutput>
     */
    @PostMapping
    public ResponseEntity<FriendsManagementSuccessOutput> handle(
            @RequestBody @Validated @NonNull final SubscribeUserRequestForm inputForm
    ){
        try{
            FriendsManagementSuccessOutput result = friendsManagementSubscribeService.subscribe(inputForm);
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
