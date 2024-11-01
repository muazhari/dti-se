package org.dti.se.module3session9.outers.repositories;

import org.dti.se.module3session9.inners.models.dao.Account;
import org.dti.se.module3session9.inners.models.dao.Session;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionRepository extends R2dbcRepository<Session, UUID> {
}