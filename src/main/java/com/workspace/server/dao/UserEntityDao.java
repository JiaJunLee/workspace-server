package com.workspace.server.dao;

import com.workspace.server.model.UserEntity;
import com.workspace.server.model.UserEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 51998 on 2018/2/26.
 */
@Repository
public interface UserEntityDao extends JpaRepository<UserEntity, UserEntityPK> {

    UserEntity findUserEntityByEmail(String email);

}
