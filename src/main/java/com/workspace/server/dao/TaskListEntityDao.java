package com.workspace.server.dao;

import com.workspace.server.model.TaskListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskListEntityDao extends JpaRepository<TaskListEntity, Long> {

    List<TaskListEntity> findAllByProjectIdOrderByListIndexAsc (long projectId) throws Exception;
    List<TaskListEntity> findAllByProjectIdAndNameContainingOrderByListIndexAsc (long projectId, String name) throws Exception;

}
