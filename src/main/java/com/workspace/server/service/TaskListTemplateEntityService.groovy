package com.workspace.server.service

import com.workspace.server.dao.TaskListTemplateEntityDao
import com.workspace.server.model.TaskListTemplateEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TaskListTemplateEntityService {

    @Autowired TaskListTemplateEntityDao taskListTemplateEntityDao

    List<TaskListTemplateEntity> findAllByProjectTemplateId (long projectTemplateId) {
        return taskListTemplateEntityDao.findAllByProjectTemplateId(projectTemplateId)
    }

}
