package friends.management.services;

import friends.management.domain.repositoryinterface.FriendsManagementRepository;
import friends.management.exception.EmptyException;
import friends.management.exception.RecordNotFoundException;
import friends.management.response.FriendsManagementReceiveOutput;
import friends.management.web.ReceiveListRequestForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class FriendsManagementReceiveListServiceImpl implements FriendsManagementReceiveListService{
    private final FriendsManagementRepository friendsManagementRepository;

    /**
     * getReceiveList
     * @param input ReceiveListRequestForm
     * @return FriendsManagementReceiveOutput
     */
    @Override
    public FriendsManagementReceiveOutput getReceiveList(ReceiveListRequestForm input) {
        //get Id by email
        Integer userId = friendsManagementRepository.getIdByEmail(input.getSender());
        if(userId == null){
            throw new RecordNotFoundException("Not found email "+ input.getSender());
        }
        List<String> emailsMention = extractEmails(input.getText());
        // select ids by emails
        if (!emailsMention.isEmpty()) {
          List<Integer> userIdsMention = friendsManagementRepository.getIdsByEmail(emailsMention);
          List<Integer> lstBlocked = new ArrayList<>();
          if (userIdsMention.size() != 0) {
            lstBlocked.addAll(friendsManagementRepository.isBlockUpdateUserOne(userId, userIdsMention));
            lstBlocked.addAll(friendsManagementRepository.isBlockUpdateUserTwo(userId, userIdsMention));
            if (lstBlocked.size() != 0) {
              List<String> emailsBlocks = friendsManagementRepository.getFriendListByUserId(lstBlocked);
              emailsMention.removeAll(emailsBlocks);
            }
          }
        }
        //receive list from userId
        List<Integer> friendIdsHasReceiveUpdate =
                friendsManagementRepository.getFriendIdsHasUpdateByUserId(userId);
        if (friendIdsHasReceiveUpdate.size() == 0){
            if(!emailsMention.isEmpty()){
                return FriendsManagementListOutputConverter.fromReceive(emailsMention);
            }
            return FriendsManagementListOutputConverter.fromReceive(Collections.emptyList());
        }
        List<String> emailListResult = friendsManagementRepository.getFriendListByUserId(friendIdsHasReceiveUpdate);
        if(!emailsMention.isEmpty()){
            emailListResult.addAll(emailsMention);
        }
        List<String> emailsReceiveWithoutDuplicates = emailListResult.stream().distinct().collect(Collectors.toList());
        return FriendsManagementListOutputConverter.fromReceive(emailsReceiveWithoutDuplicates);
    }
    private List<String> extractEmails(String text){
        Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(text);
        List<String> emailsInText = new ArrayList<>();
        while (m.find()) {
            emailsInText.add(m.group());
        }
        return emailsInText;
    }
}
