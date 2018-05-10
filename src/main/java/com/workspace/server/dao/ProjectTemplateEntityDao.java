package com.workspace.server.dao;

import com.workspace.server.model.ProjectTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTemplateEntityDao extends JpaRepository<ProjectTemplateEntity, Long> {

}
