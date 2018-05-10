package com.workspace.server.dao;

import com.workspace.server.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskEntityDao extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findAllByTaskListId (long taskListId) throws Exception;
    List<TaskEntity> findAllByTaskListIdAndContentContaining (long taskListId, String content) throws Exception;

}
