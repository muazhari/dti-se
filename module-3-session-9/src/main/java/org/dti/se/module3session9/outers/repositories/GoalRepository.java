package org.dti.se.module3session9.outers.repositories;

import org.dti.se.module3session9.inners.models.dao.Goal;
import org.dti.se.module3session9.inners.models.dao.Wallet;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GoalRepository extends R2dbcRepository<Goal, UUID> {
}