package friends.management.web;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ReceiveListRequestForm {
    @Getter
    @Setter
    @NotNull(message = "please type a sender email address")
    @NotBlank(message = "sender must not be blank")
    private String sender;
    @Getter
    @Setter
    private String text;
}
