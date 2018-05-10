package com.workspace.server.controller

import com.workspace.server.exception.ServerException
import com.workspace.server.interceptor.ContentFormatInterceptor
import com.workspace.server.interceptor.RequestContentInterceptor
import com.workspace.server.model.TaskEntity
import com.workspace.server.model.UserEntity
import com.workspace.server.model.WorkspaceStatus
import com.workspace.server.security.utils.JWT
import com.workspace.server.service.TaskEntityService
import com.workspace.server.service.UserEntityService
import com.workspace.server.util.ContentFormatter
import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.Subject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.text.SimpleDateFormat

@RestController
@RequestMapping('/task')
class TaskController {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat('yyyy-MM-dd HH:mm:ss')

    @Autowired TaskEntityService taskEntityService
    @Autowired UserEntityService userEntityService
    @Autowired JWT jwt

    @RequestMapping('/create')
    String create (@RequestAttribute(value = ContentFormatInterceptor.CONTENT_FORMATTER) ContentFormatter contentFormatter, @RequestAttribute(value = RequestContentInterceptor.JSON_CONTENT) def jsonRequestContent) {
        Subject subject = SecurityUtils.getSubject()
        if (subject.isAuthenticated()) {
            long userId = jwt?.getId(subject?.getPrincipal()?.toString())
            String content = jsonRequestContent.'content'
            int type = Integer.valueOf(jsonRequestContent.'type'.toString())
            Date expiry = null
            if (jsonRequestContent.'expiry') {
                expiry = dateFormat.parse(jsonRequestContent.'expiry')
            }
            long taskListId = Long.valueOf(jsonRequestContent.'task_list_id'.toString())

            TaskEntity taskEntity = new TaskEntity(
                    content: content,
                    type: type,
                    expiryDatetime: expiry,
                    taskListId: taskListId,
                    ownerUserId: userId
            )

            taskEntityService.save(taskEntity)

            contentFormatter.content().'workspace_content' {
                'task' (
                        id: taskEntity.id,
                        type: taskEntity.type,
                        expiry: taskEntity.expiryDatetime ? dateFormat?.format(taskEntity.expiryDatetime) : '',
                        task_list_id: taskEntity.taskListId,
                        owner_user_id: taskEntity.ownerUserId
                )
                'status_code' WorkspaceStatus.SUCCESS
            }

        } else {
            throw new ServerException(ServerException.ServerExceptionCode.ACCESS_RESTRICTED)
        }
        return contentFormatter?.toString()
    }

    @RequestMapping('/find-all')
    String findAllByTaskListId (@RequestAttribute(value = ContentFormatInterceptor.CONTENT_FORMATTER) ContentFormatter contentFormatter, @RequestAttribute(value = RequestContentInterceptor.JSON_CONTENT) def jsonRequestContent) {
        Subject subject = SecurityUtils.getSubject()
        if (subject.isAuthenticated()) {
            long taskListId = Long.valueOf(jsonRequestContent.'task_list_id'.toString())
            List<TaskEntity> taskEntityList = taskEntityService.findAllByTaskListId(taskListId)

            contentFormatter.content().'workspace_content' {
                'tasks' taskEntityList, { TaskEntity taskEntity ->

                    UserEntity userEntity = userEntityService.findUserEntityById(taskEntity.ownerUserId)

                    'id' taskEntity.id
                    'type' taskEntity.type
                    'content' taskEntity.content
                    'expiry' taskEntity.expiryDatetime ? dateFormat.format(taskEntity.expiryDatetime) : ''
                    'task_list_id' taskEntity.taskListId
                    'owner_user_id' taskEntity.ownerUserId
                    'icon_file_id' userEntity.iconFileId
                }
                'status_code' WorkspaceStatus.SUCCESS
            }
        } else {
            throw new ServerException(ServerException.ServerExceptionCode.ACCESS_RESTRICTED)
        }
        return contentFormatter?.toString()
    }

}
