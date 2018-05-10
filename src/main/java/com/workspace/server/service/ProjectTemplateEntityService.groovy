package com.workspace.server.service

import com.workspace.server.dao.ProjectTemplateEntityDao
import com.workspace.server.model.ProjectTemplateEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = Exception.class)
class ProjectTemplateEntityService {

    @Autowired ProjectTemplateEntityDao projectTemplateEntityDao

    List<ProjectTemplateEntity> findAll () {
        return projectTemplateEntityDao?.findAll()
    }

}
