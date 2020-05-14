package friends.management.web;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * BlockUserRequestForm
 */
@Data
public class BlockUserRequestForm {

	@Getter
	@Setter
	@NotNull(message = "Please input a requestor email address")
	@NotBlank(message = "requestor name must not be blank")
	private String requestor;
	@Getter
	@Setter
	@NotNull(message = "Please input a target email address")
	@NotBlank(message = "target name must not be blank")
	private String target;
}
