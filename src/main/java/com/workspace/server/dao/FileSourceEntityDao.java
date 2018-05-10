package com.workspace.server.dao;

import com.workspace.server.model.FileSourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileSourceEntityDao extends JpaRepository<FileSourceEntity, Long> {

    FileSourceEntity findFileSourceEntityByMd5 (String md5);

}
