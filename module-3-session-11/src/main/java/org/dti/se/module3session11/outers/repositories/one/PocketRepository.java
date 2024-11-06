package org.dti.se.module3session11.outers.repositories.one;

import org.dti.se.module3session11.inners.models.entities.Pocket;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PocketRepository extends R2dbcRepository<Pocket, UUID> {
}