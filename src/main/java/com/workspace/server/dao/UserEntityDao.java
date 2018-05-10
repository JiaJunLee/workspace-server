package com.workspace.server.dao;

import com.workspace.server.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityDao extends JpaRepository<UserEntity, Long> {

    UserEntity findUserEntityByEmail(String email);

}
