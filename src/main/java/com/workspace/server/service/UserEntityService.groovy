package com.workspace.server.service

import com.workspace.server.dao.TeamEntityDao
import com.workspace.server.dao.UserEntityDao
import com.workspace.server.model.TeamEntity
import com.workspace.server.model.UserEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserEntityService {

    @Autowired UserEntityDao userEntityDao
    @Autowired TeamEntityDao teamEntityDao

    UserEntity findUserEntityByEmail (String email) {
        return userEntityDao.findUserEntityByEmail(email)
    }

    UserEntity findUserEntityById (long id) {
        return userEntityDao.getOne(id)
    }

    UserEntity save (UserEntity userEntity){
        userEntityDao.saveAndFlush(userEntity)

        TeamEntity teamEntity = new TeamEntity(
                name: "${userEntity?.name}'s Group",
                introduce: 'Default Group',
                administratorUserId: userEntity?.id
        )

        teamEntityDao.saveAndFlush(teamEntity)
        return userEntity
    }

    boolean exists (String email) {
        return userEntityDao.findUserEntityByEmail(email) != null
    }

}
