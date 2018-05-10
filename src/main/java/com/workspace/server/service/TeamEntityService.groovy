package com.workspace.server.service

import com.workspace.server.dao.TeamEntityDao
import com.workspace.server.model.TeamEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TeamEntityService {

    @Autowired TeamEntityDao teamEntityDao

    List<TeamEntity> findAllByAdministratorUserId (long userId) {
        return teamEntityDao.findAllByAdministratorUserId (userId)
    }

}
