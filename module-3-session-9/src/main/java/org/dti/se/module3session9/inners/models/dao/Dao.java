package org.dti.se.module3session9.inners.models.dao;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Data
@SuperBuilder
@NoArgsConstructor
@Accessors(chain = true)
public class Dao {
    protected ZonedDateTime createdAt;
    protected ZonedDateTime updatedAt;

    public Dao(ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.createdAt = createdAt.truncatedTo(ChronoUnit.MICROS);
        this.updatedAt = updatedAt.truncatedTo(ChronoUnit.MICROS);
    }

    public abstract static class DaoBuilder<C extends Dao, B extends DaoBuilder<C, B>> {

        public B createdAt(ZonedDateTime createdAt) {
            this.createdAt = createdAt.truncatedTo(ChronoUnit.MICROS);
            return this.self();
        }

        public B updatedAt(ZonedDateTime updatedAt) {
            this.updatedAt = updatedAt.truncatedTo(ChronoUnit.MICROS);
            return this.self();
        }
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt.truncatedTo(ChronoUnit.MICROS);
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt.truncatedTo(ChronoUnit.MICROS);
    }

    @SuppressWarnings("unchecked")
    public <T> T patchFrom(T object) {
        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(object);
                if (value != null) {
                    field.set(this, value);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return (T) this;
    }
}
