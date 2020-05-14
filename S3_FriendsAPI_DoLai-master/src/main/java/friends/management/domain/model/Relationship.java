package friends.management.domain.model;

import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.util.Optional;

/**
 * Relationship
 */
public class Relationship {
    @Setter
    private Integer user_one;
    @Setter
    private Integer user_two;
    private StatusEnum status;
    private Instant createdAt;
    private Instant updatedAt;

    /**
     * getCreatedAt
     * @return Instant
     */
    public Instant getCreatedAt() {
        return Optional.ofNullable(this.createdAt)
                .map(instant -> instant.toEpochMilli())
                .map(Instant::ofEpochMilli)
                .orElse(null);
    }

    /**
     * getUpdatedAt
     * @return Instant
     */
    public Instant getUpdatedAt() {
        return Optional.ofNullable(this.updatedAt)
                .map(instant -> instant.toEpochMilli())
                .map(Instant::ofEpochMilli)
                .orElse(null);
    }
}
