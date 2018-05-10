package com.workspace.server.controller

import com.workspace.server.interceptor.ContentFormatInterceptor
import com.workspace.server.model.FileSourceEntity
import com.workspace.server.model.WorkspaceStatus
import com.workspace.server.service.FileSourceEntityService
import com.workspace.server.util.ContentFormatter
import com.workspace.server.util.FileUtil
import org.apache.commons.io.FileUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping('/source')
class FileSourceController {

    @Autowired FileUtil fileUtil
    @Autowired FileSourceEntityService fileSourceEntityService

    @RequestMapping('/upload')
    String upload (@RequestParam("file") MultipartFile file, @RequestAttribute(value = ContentFormatInterceptor.CONTENT_FORMATTER) ContentFormatter contentFormatter) {

        String fileMd5 = fileUtil.getFileMd5(file)
        FileSourceEntity fileSourceEntity = fileSourceEntityService.findByMd5(fileMd5)

        if (fileSourceEntity == null) {

            File outputFile = fileUtil.getFile(fileMd5)
            if (!outputFile?.exists()) {
                outputFile.createNewFile()
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), outputFile)

            fileSourceEntity = new FileSourceEntity(
                    name: file.originalFilename,
                    md5: fileMd5,
                    type: fileUtil.getFileType(file.originalFilename),
                    size: file.getSize()
            )

            fileSourceEntityService.save(fileSourceEntity)
        }

        contentFormatter.content().'workspace_content' {
            'file_source' {
                'id' fileSourceEntity.id
                'name' fileSourceEntity.name
                'md5' fileSourceEntity.md5
                'type' fileSourceEntity.type
                'size' fileSourceEntity.size
            }
            'result_code' fileSourceEntity ? WorkspaceStatus.SUCCESS : WorkspaceStatus.FAIL
            'status_code' WorkspaceStatus.SUCCESS
        }

        return contentFormatter?.toString()
    }

    @RequestMapping('/{id}')
    ResponseEntity<InputStreamResource> getSource (@PathVariable Long id) {
        FileSourceEntity fileSourceEntity = fileSourceEntityService.getOne(id)
        String filePath = fileUtil.getFile(fileSourceEntity.getMd5())?.getAbsolutePath()
        FileSystemResource file = new FileSystemResource(filePath)
        HttpHeaders headers = new HttpHeaders()
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate")
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileSourceEntity.getName()))
        headers.add("Pragma", "no-cache")
        headers.add("Expires", "0")

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()))
    }

}
