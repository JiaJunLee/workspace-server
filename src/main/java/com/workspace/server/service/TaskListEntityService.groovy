package com.workspace.server.service

import com.workspace.server.dao.TaskListEntityDao
import com.workspace.server.model.TaskListEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TaskListEntityService {

    @Autowired TaskListEntityDao taskListEntityDao

    List<TaskListEntity> saveAll (List<TaskListEntity> taskListEntityList) {
        taskListEntityList?.each { taskListEntityDao.saveAndFlush(it) }
        return taskListEntityList
    }

    List<TaskListEntity> findAllByProjectIdOrderByListIndexAsc (long projectId) {
        return taskListEntityDao.findAllByProjectIdOrderByListIndexAsc(projectId)
    }

    List<TaskListEntity> findAllByProjectIdAndNameContainingOrderByListIndexAsc (long projectId, String name) {
        return taskListEntityDao.findAllByProjectIdAndNameContainingOrderByListIndexAsc(projectId, name)
    }

}
