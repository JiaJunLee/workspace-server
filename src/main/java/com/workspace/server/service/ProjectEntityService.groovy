package com.workspace.server.service

import com.workspace.server.dao.ProjectEntityDao
import com.workspace.server.model.ProjectEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProjectEntityService {

    @Autowired ProjectEntityDao projectEntityDao

    List<ProjectEntity> findAllByTeamId (long teamId) {
        return projectEntityDao.findAllByTeamId (teamId)
    }

    List<ProjectEntity> findAllByTeamIdAndVisibility (long teamId, int visibility) {
        return projectEntityDao.findAllByTeamIdAndVisibility (teamId, visibility)
    }

    List<ProjectEntity> findAllByTeamIdAndVisibilityIn (long teamId, List<Integer> visibilityList) {
        return projectEntityDao.findAllByTeamIdAndVisibilityIn (teamId, visibilityList)
    }

    ProjectEntity save (ProjectEntity projectEntity) {
        return projectEntityDao.saveAndFlush(projectEntity)
    }

    List<ProjectEntity> findAllByTeamIdAndNameContaining (long teamId, String name) {
        return projectEntityDao.findAllByTeamIdAndNameContaining (teamId, name)
    }

}
