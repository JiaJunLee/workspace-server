package com.workspace.server.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

import javax.persistence.*

@Entity
@Table(name = "task_list", schema = "workspace_db", catalog = "")
@EntityListeners(AuditingEntityListener.class)
class TaskListEntity {
    
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id
    
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    String name
    
    @Basic
    @Column(name = "type", nullable = false)
    short type

    @Basic
    @Column(name = "list_index", nullable = false)
    int listIndex

    @Basic
    @Column(name = "project_id", nullable = false)
    long getProjectId() {
        return projectId
    }
    long projectId

    @Basic
    @Column(name = "create_datetime", nullable = false)
    @CreatedDate
    Date createDatetime
    
}
