package org.dti.se.module3session11.outers.repositories.ones;

import org.dti.se.module3session11.inners.models.entities.Goal;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GoalRepository extends R2dbcRepository<Goal, UUID> {
}