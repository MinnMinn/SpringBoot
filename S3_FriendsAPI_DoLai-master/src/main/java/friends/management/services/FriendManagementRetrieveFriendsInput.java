package friends.management.services;

import lombok.*;

/**
 * FriendManagementRetrieveFriendsInput
 */
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class FriendManagementRetrieveFriendsInput {
    @Getter
    @Setter
    private String email;
}
