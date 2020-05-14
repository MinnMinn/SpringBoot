package friends.management.domain.model;

import lombok.*;
import java.time.Instant;
import java.util.Optional;

/**
 * User
 */
public class User {
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private String email;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

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

    /**
     * getDeletedAt
     * @return Instant
     */
    public Instant getDeletedAt() {
        return Optional.ofNullable(this.deletedAt)
                .map(instant -> instant.toEpochMilli())
                .map(Instant::ofEpochMilli)
                .orElse(null);
    }
}


