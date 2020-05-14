package friends.management.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * FriendsManagementReceiveOutput
 */
@Data
@RequiredArgsConstructor
public class FriendsManagementReceiveOutput {
    private final Boolean success;
    private final List<String> recipients;
}
