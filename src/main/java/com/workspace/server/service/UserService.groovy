package com.workspace.server.service

import com.workspace.server.dao.UserDao
import com.workspace.server.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.sql.SQLIntegrityConstraintViolationException

/**
 * Created by 51998 on 2017/12/30.
 */
@Service
class UserService {

    @Autowired UserDao userDao

    User createUser(){
        return userDao.saveAndFlush(new User(name: UUID.randomUUID().toString()))
    }

    User getUser(String username){
        return userDao.findUserByUsername(username)
    }

    @Transactional
    void updateUsers(){
        throw new SQLIntegrityConstraintViolationException('HEHE', 'SELECT * FROM USER')
    }

}
