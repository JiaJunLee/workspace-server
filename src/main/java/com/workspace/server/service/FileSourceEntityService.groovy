package com.workspace.server.service

import com.workspace.server.dao.FileSourceEntityDao
import com.workspace.server.model.FileSourceEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = Exception.class)
class FileSourceEntityService {

    @Autowired FileSourceEntityDao fileSourceEntityDao

    FileSourceEntity save (FileSourceEntity fileSourceEntity) {
        return fileSourceEntityDao.saveAndFlush(fileSourceEntity)
    }

    FileSourceEntity getOne(Long id) {
        return fileSourceEntityDao.getOne(id)
    }

    FileSourceEntity findByMd5 (String md5) {
        return fileSourceEntityDao.findFileSourceEntityByMd5(md5)
    }

}
