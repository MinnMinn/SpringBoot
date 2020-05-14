package friends.management.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Data
@RequiredArgsConstructor
public class FriendsManagementListOutput {
    private final Boolean success;
    private final Integer count;
    private final List<String> friends;
}
