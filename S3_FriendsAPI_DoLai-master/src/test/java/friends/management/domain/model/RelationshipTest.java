package friends.management.domain.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class RelationshipTest {
    static final Integer USER_ONE_ID = 1;
    static final Integer USER_TWO_ID = 2;

    /** normal case getCreate */
    @Test
    public void getCreatedAt() {
      final Relationship relationship = new Relationship();
      relationship.setUser_one(USER_ONE_ID);
      relationship.setUser_two(USER_TWO_ID);
      assertThat(relationship.getCreatedAt()).isNull();
    }

    /** normal case getUpdate */
    @Test
    public void getUpdateAt() {
      final Relationship relationship = new Relationship();
      relationship.setUser_one(USER_ONE_ID);
      relationship.setUser_two(USER_TWO_ID);
      assertThat(relationship.getUpdatedAt()).isNull();
    }
}
