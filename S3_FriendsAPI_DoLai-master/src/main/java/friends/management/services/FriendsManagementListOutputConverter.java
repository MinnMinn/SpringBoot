package friends.management.services;

import friends.management.response.FriendsManagementListOutput;
import friends.management.response.FriendsManagementReceiveOutput;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class FriendsManagementListOutputConverter {
    public static FriendsManagementListOutput from(@NonNull final List<String> emails) {
        Objects.requireNonNull(emails);
        return new FriendsManagementListOutput(
                true,
                emails.size(),
                emails
        );
    }
    public static FriendsManagementReceiveOutput fromReceive(@NonNull final List<String> emails){
        Objects.requireNonNull(emails);
        return new FriendsManagementReceiveOutput(
                true,
                emails
        );
    }
}
