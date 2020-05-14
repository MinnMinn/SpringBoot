package friends.management.services;

import lombok.*;
import java.util.List;

/**
 * FriendManagementConnectInput
 */
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class FriendManagementConnectInput {
    @Getter
    @Setter
    private List<String> friends;
}
