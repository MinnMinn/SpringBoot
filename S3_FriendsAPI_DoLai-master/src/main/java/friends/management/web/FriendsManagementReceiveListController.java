package friends.management.web;

import friends.management.exception.EmptyException;
import friends.management.exception.RecordNotFoundException;
import friends.management.response.FriendsManagementListOutput;
import friends.management.response.FriendsManagementReceiveOutput;
import friends.management.services.FriendsManagementReceiveListService;
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
@RequestMapping("friends-management/receive")
@RestController
@RequiredArgsConstructor
public class FriendsManagementReceiveListController {
    private final FriendsManagementReceiveListService friendsManagementReceiveListService;

    private final Logger logger = LoggerFactory.getLogger(FriendsManagementReceiveListController.class);

    /**
     * handle
     * @param inputForm ReceiveListRequestForm
     * @return ResponseEntity<FriendsManagementReceiveOutput>
     */
    @PostMapping
    public ResponseEntity<FriendsManagementReceiveOutput> handle(
            @RequestBody @Validated @NonNull final ReceiveListRequestForm inputForm
    ){
        try {
            FriendsManagementReceiveOutput result = friendsManagementReceiveListService.getReceiveList(inputForm);
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
