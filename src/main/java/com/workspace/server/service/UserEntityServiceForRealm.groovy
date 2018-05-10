package com.workspace.server.service

import com.workspace.server.dao.UserEntityDao
import com.workspace.server.model.UserEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserEntityServiceForRealm {

    @Autowired UserEntityDao userEntityDao

    UserEntity findUserEntityByEmail (String email) {
        return userEntityDao.findUserEntityByEmail(email)
    }

    boolean exists (String email) {
        return userEntityDao.findUserEntityByEmail(email) != null
    }

}
