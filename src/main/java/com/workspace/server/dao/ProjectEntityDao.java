package com.workspace.server.dao;

import com.workspace.server.model.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjectEntityDao extends JpaRepository<ProjectEntity, Long> {

    List<ProjectEntity> findAllByTeamId (long teamId) throws Exception;
    List<ProjectEntity> findAllByTeamIdAndVisibility (long teamId, int visibility) throws Exception;
    List<ProjectEntity> findAllByTeamIdAndVisibilityIn (long teamId, List<Integer> visibilityList) throws Exception;
    List<ProjectEntity> findAllByTeamIdAndNameContaining (long teamId, String name) throws Exception;

}
