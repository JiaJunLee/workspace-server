package com.workspace.server.dao;

import com.workspace.server.model.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TeamEntityDao extends JpaRepository<TeamEntity, Long> {

    List<TeamEntity> findAllByAdministratorUserId (long administratorUserId) throws Exception;
}
