package com.workspace.server.controller

import com.workspace.server.interceptor.ContentFormatInterceptor
import com.workspace.server.model.ProjectTemplateEntity
import com.workspace.server.model.WorkspaceStatus
import com.workspace.server.service.ProjectTemplateEntityService
import com.workspace.server.util.ContentFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/project-template')
class ProjectTemplateController {

    @Autowired ProjectTemplateEntityService projectTemplateEntityService

    @RequestMapping('/all')
    String all (@RequestAttribute(value = ContentFormatInterceptor.CONTENT_FORMATTER) ContentFormatter contentFormatter) {
        List<ProjectTemplateEntity> projectTemplateEntityList = projectTemplateEntityService?.findAll()
        contentFormatter.content().'workspace_content' {
            'template' projectTemplateEntityList, { ProjectTemplateEntity currentProjectTemplate ->
                'id' currentProjectTemplate?.id
                'name' currentProjectTemplate?.name
                'introduce' currentProjectTemplate?.introduce
                'icon_file_id' currentProjectTemplate?.iconFileId
            }
            'status_code' WorkspaceStatus.SUCCESS
        }
        return contentFormatter?.toString()
    }

}
