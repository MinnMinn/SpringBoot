package friends.management.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import friends.management.services.FriendManagementConnectInput;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * UserFriendsRequestForm
 */
@Data
public class UserFriendsRequestForm {
    @JsonProperty("friends")
    @NotNull(message = "Please input friends")
    private List<String> friends;

    /**
     * toInput
     * @return
     */
    public FriendManagementConnectInput toInput(){
        return new FriendManagementConnectInput(this.friends);
    }
}
