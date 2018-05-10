package com.workspace.server.controller

import com.workspace.server.exception.ServerException
import com.workspace.server.interceptor.ContentFormatInterceptor
import com.workspace.server.interceptor.RequestContentInterceptor
import com.workspace.server.model.ProjectEntity
import com.workspace.server.model.TaskListEntity
import com.workspace.server.model.TaskListTemplateEntity
import com.workspace.server.model.WorkspaceStatus
import com.workspace.server.security.utils.JWT
import com.workspace.server.service.ProjectEntityService
import com.workspace.server.service.TaskListEntityService
import com.workspace.server.service.TaskListTemplateEntityService
import com.workspace.server.util.ContentFormatter
import groovy.util.logging.Slf4j
import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.Subject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/project')
@Slf4j
class ProjectController {

    @Autowired JWT jwt
    @Autowired ProjectEntityService projectEntityService
    @Autowired TaskListTemplateEntityService taskListTemplateEntityService
    @Autowired TaskListEntityService taskListEntityService

    @RequestMapping('/find-all')
    String findAllByGroupIdAndVisibilityIn (@RequestAttribute(value = ContentFormatInterceptor.CONTENT_FORMATTER) ContentFormatter contentFormatter, @RequestAttribute(value = RequestContentInterceptor.JSON_CONTENT) def jsonRequestContent) {
        Subject subject = SecurityUtils.getSubject()
        if (subject.isAuthenticated()) {
            String teamId = jsonRequestContent.'team_id'
            List<ProjectEntity> projectEntityList = projectEntityService.findAllByTeamId(Long.valueOf(teamId.toString()))
            contentFormatter.content().'workspace_content' {
                'projects' projectEntityList, { ProjectEntity projectEntity ->
                    'id' projectEntity?.id
                    'name' projectEntity?.name
                    'introduce' projectEntity?.introduce
                    'visibility' projectEntity?.visibility
                    'owner_user_id' projectEntity?.ownerUserId
                    'icon_file_id' projectEntity?.iconFileId
                }
                'status_code' WorkspaceStatus.SUCCESS
            }
        } else {
            throw new ServerException(ServerException.ServerExceptionCode.ACCESS_RESTRICTED)
        }
        return contentFormatter?.toString()
    }

    @RequestMapping('/create')
    @Transactional
    String create (@RequestAttribute(value = ContentFormatInterceptor.CONTENT_FORMATTER) ContentFormatter contentFormatter, @RequestAttribute(value = RequestContentInterceptor.JSON_CONTENT) def jsonRequestContent) {
        Subject subject = SecurityUtils.getSubject()
        if (subject?.isAuthenticated()) {
            long userId = jwt?.getId(subject?.getPrincipal()?.toString())
            String name = jsonRequestContent.'name'
            String introduce = jsonRequestContent.'introduce'
            int visibility = Integer.valueOf(jsonRequestContent.'visibility'.toString())
            long iconFileId = Long.valueOf(jsonRequestContent.'icon_file_id'.toString())
            long teamId = Long.valueOf(jsonRequestContent.'team_id'.toString())
            long projectTemplateId = Long.valueOf(jsonRequestContent.'project_template_id'.toString())

            if (visibility in [ProjectEntity.VISIBILITY_PUBLIC, ProjectEntity.VISIBILITY_PRIVATE]) {

                ProjectEntity projectEntity = new ProjectEntity(
                        name: name,
                        introduce: introduce,
                        visibility: visibility,
                        ownerUserId: userId,
                        iconFileId: iconFileId,
                        teamId: teamId
                )

                projectEntityService.save(projectEntity)

                List<TaskListTemplateEntity> taskListTemplateEntityList = taskListTemplateEntityService.findAllByProjectTemplateId(projectTemplateId)
                List<TaskListEntity> taskListEntityList = []
                taskListTemplateEntityList?.each { currentTaskListTemplate ->
                    TaskListEntity taskListEntity = new TaskListEntity(
                            name: currentTaskListTemplate?.taskListName,
                            type: currentTaskListTemplate?.taskListType,
                            listIndex: currentTaskListTemplate?.taskListIndex,
                            projectId: projectEntity?.id
                    )
                    taskListEntityList.add(taskListEntity)
                }

                taskListEntityService.saveAll(taskListEntityList)

                contentFormatter.content().'workspace_content' {
                    'project' (
                            id: projectEntity.id,
                            name: projectEntity.name,
                            introduce: projectEntity.introduce,
                            visibility: projectEntity.visibility,
                            ownerUserId: projectEntity.ownerUserId,
                            iconFileId: projectEntity.iconFileId,
                            teamId: projectEntity.teamId
                    )
                    'status_code' WorkspaceStatus.SUCCESS
                }

                log.info("[workspace-server] User Id ${userId}, Create Project Entity ${name}")

            } else {
                throw new ServerException(ServerException.ServerExceptionCode.UNKNOWN_REQUEST)
            }
        } else {
            throw new ServerException(ServerException.ServerExceptionCode.ACCESS_RESTRICTED)
        }
        return contentFormatter?.toString()
    }

}
