package org.dti.se.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Data
@SuperBuilder
@NoArgsConstructor
public class Dao {
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public Dao(OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.createdAt = createdAt.truncatedTo(ChronoUnit.MICROS);
        this.updatedAt = updatedAt.truncatedTo(ChronoUnit.MICROS);
    }

    public abstract static class DaoBuilder<C extends Dao, B extends DaoBuilder<C, B>> {

        public B createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt.truncatedTo(ChronoUnit.MICROS);
            return this.self();
        }

        public B updatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt.truncatedTo(ChronoUnit.MICROS);
            return this.self();
        }
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt.truncatedTo(ChronoUnit.MICROS);
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt.truncatedTo(ChronoUnit.MICROS);
    }
}
