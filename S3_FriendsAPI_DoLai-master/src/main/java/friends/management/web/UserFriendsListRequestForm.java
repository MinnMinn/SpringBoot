package friends.management.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import friends.management.services.FriendManagementRetrieveFriendsInput;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * UserFriendsListRequestForm
 */
@Data
public class UserFriendsListRequestForm {

	@Getter
	@Setter
	@NotNull(message = "Please input a email")
	@NotBlank(message = "email must not be blank")
	@JsonProperty("email")
	private String email;

	/**
	 * toInput
	 * @return
	 */
	public FriendManagementRetrieveFriendsInput toInput(){
		return new FriendManagementRetrieveFriendsInput(this.email);
	}
}
