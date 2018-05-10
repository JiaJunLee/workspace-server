package com.workspace.server.service

import com.workspace.server.dao.TaskEntityDao
import com.workspace.server.model.TaskEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TaskEntityService {

    @Autowired TaskEntityDao taskEntityDao

    TaskEntity save (TaskEntity taskEntity) {
        return taskEntityDao.saveAndFlush(taskEntity)
    }

    List<TaskEntity> findAllByTaskListId (long taskListId) {
        return taskEntityDao.findAllByTaskListId (taskListId)
    }

    List<TaskEntity> findAllByTaskListIdAndContentContaining (long taskListId, String content) {
        return taskEntityDao.findAllByTaskListIdAndContentContaining(taskListId, content)
    }

}
