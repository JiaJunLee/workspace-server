package com.workspace.server.dao;

import com.workspace.server.model.TaskListTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskListTemplateEntityDao extends JpaRepository<TaskListTemplateEntity, Long> {

    List<TaskListTemplateEntity> findAllByProjectTemplateId (long projectTemplateId) throws Exception;

}
