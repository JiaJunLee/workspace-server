package com.workspace.server.controller

import com.workspace.server.exception.ServerException
import com.workspace.server.interceptor.ContentFormatInterceptor
import com.workspace.server.interceptor.RequestContentInterceptor
import com.workspace.server.model.TaskListEntity
import com.workspace.server.model.WorkspaceStatus
import com.workspace.server.service.TaskListEntityService
import com.workspace.server.util.ContentFormatter
import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.Subject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/task-list')
class TaskListController {

    @Autowired TaskListEntityService taskListEntityService

    @RequestMapping('/find-all')
    String findAllByProjectIdOrderByListIndexAsc (@RequestAttribute(value = ContentFormatInterceptor.CONTENT_FORMATTER) ContentFormatter contentFormatter, @RequestAttribute(value = RequestContentInterceptor.JSON_CONTENT) def jsonRequestContent) {
        Subject subject = SecurityUtils.getSubject()
        if (subject?.isAuthenticated()) {
            long projectId = Long.valueOf(jsonRequestContent.'project_id'.toString())
            if (projectId) {
                List<TaskListEntity> taskListEntityList = taskListEntityService.findAllByProjectIdOrderByListIndexAsc(projectId)
                contentFormatter.content().'workspace_content' {
                    'task_lists' taskListEntityList, { TaskListEntity taskListEntity ->
                        'id' taskListEntity.id
                        'name' taskListEntity.name
                        'type' taskListEntity.type
                        'list_index' taskListEntity.listIndex
                    }
                    'status_code' WorkspaceStatus.SUCCESS
                }
            } else {
                throw new ServerException(ServerException.ServerExceptionCode.UNKNOWN_REQUEST)
            }
        } else {
            throw new ServerException(ServerException.ServerExceptionCode.ACCESS_RESTRICTED)
        }
        return contentFormatter?.toString()
    }

}
