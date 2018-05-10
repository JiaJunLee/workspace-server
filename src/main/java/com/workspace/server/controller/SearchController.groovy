package com.workspace.server.controller

import com.hankcs.hanlp.HanLP
import com.workspace.server.exception.ServerException
import com.workspace.server.interceptor.ContentFormatInterceptor
import com.workspace.server.interceptor.RequestContentInterceptor
import com.workspace.server.model.ProjectEntity
import com.workspace.server.model.TaskEntity
import com.workspace.server.model.TaskListEntity
import com.workspace.server.model.WorkspaceStatus
import com.workspace.server.service.ProjectEntityService
import com.workspace.server.service.TaskEntityService
import com.workspace.server.service.TaskListEntityService
import com.workspace.server.util.ContentFormatter
import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.Subject
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/search')
class SearchController {

    @Autowired ProjectEntityService projectEntityService
    @Autowired TaskListEntityService taskListEntityService
    @Autowired TaskEntityService taskEntityService

    @RequestMapping('/hanlp')
    String search (@RequestAttribute(value = ContentFormatInterceptor.CONTENT_FORMATTER) ContentFormatter contentFormatter, @RequestAttribute(value = RequestContentInterceptor.JSON_CONTENT) def jsonRequestContent) {
        Subject subject = SecurityUtils.getSubject()
        if (subject.isAuthenticated()) {
            String searchContent = jsonRequestContent.'search_content'
            long teamId = Long.valueOf(jsonRequestContent.'team_id'.toString())
            List<String> keywordList = HanLP.extractKeyword(searchContent, 3)

            List<ProjectEntity> projectEntityList =  []
            List<TaskListEntity> taskListEntityList = []
            List<TaskEntity> taskEntityList = []

            keywordList?.each { currentKeyword ->
                projectEntityList.addAll(projectEntityService.findAllByTeamIdAndNameContaining(teamId, currentKeyword))
            }

            projectEntityService.findAllByTeamId(teamId)?.each { currentProjectEntity ->
                keywordList?.each { currentKeyword ->
                    taskListEntityList.addAll(taskListEntityService.findAllByProjectIdAndNameContainingOrderByListIndexAsc (currentProjectEntity?.id, currentKeyword))
                }
            }

            projectEntityService.findAllByTeamId(teamId)?.each { currentProjectEntity ->
                taskListEntityService.findAllByProjectIdOrderByListIndexAsc(currentProjectEntity.id)?.each { currentTaskList ->
                    keywordList?.each { currentKeyword ->
                        taskEntityList.addAll(taskEntityService.findAllByTaskListIdAndContentContaining (currentTaskList.id, currentKeyword))
                    }
                }
            }

            contentFormatter.content().'workspace_content' {
                'projects' projectEntityList, { ProjectEntity projectEntity ->
                    'id' projectEntity.id
                    'name' projectEntity.name
                }
                'task_lists' taskListEntityList, { TaskListEntity taskListEntity ->
                    'id' taskListEntity.id
                    'name' taskListEntity.name
                }
                'tasks' taskEntityList, { TaskEntity taskEntity ->
                    'id' taskEntity.id
                    'content' taskEntity.content
                }
                'status_code' WorkspaceStatus.SUCCESS
            }
        } else {
            throw new ServerException(ServerException.ServerExceptionCode.ACCESS_RESTRICTED)
        }
        return contentFormatter?.toString()
    }

    @Test
    void testHanLP () {
        String text = 'Java是一门优秀的语言'
        List<String> keywordList = HanLP.extractKeyword(text, 3)
        System.out.println(keywordList)
    }

}
