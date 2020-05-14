package friends.management.domain.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class UserTest {

    static final Integer SAMPLE_ID = 1;
    static final String SAMPLE_EMAIL = "test@gmail.com";

    /**
     * normal case getCreate
     */

    @Test
    public void getCreatedAt(){
        final User user = new User();
        user.setId(SAMPLE_ID);
        user.setEmail(SAMPLE_EMAIL);
        assertThat(user.getCreatedAt()).isNull();
    }

    /**
     * normal case getUpdate
     */
    @Test
    public void getUpdateAt(){
        final User user = new User();
        user.setId(SAMPLE_ID);
        user.setId(SAMPLE_ID);
        user.setEmail(SAMPLE_EMAIL);
        assertThat(user.getUpdatedAt()).isNull();
    }

    /**
     * normal case getDeleted
     */
    @Test
    public void getDeleteAt(){
        final User user = new User();
        user.setId(SAMPLE_ID);
        user.setId(SAMPLE_ID);
        user.setEmail(SAMPLE_EMAIL);
        assertThat(user.getDeletedAt()).isNull();
    }
}
