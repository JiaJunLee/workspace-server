package com.workspace.server.dao;

import com.workspace.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 51998 on 2017/12/30.
 */
@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    User findUserByUsername(String username);

}
