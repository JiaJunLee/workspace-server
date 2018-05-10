package com.workspace.server.model

import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Entity
@Table(name = "task_list_template", schema = "workspace_db", catalog = "")
@EntityListeners(AuditingEntityListener.class)
class TaskListTemplateEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id

    @Basic
    @Column(name = "task_list_name", nullable = true, length = 255)
    String taskListName

    @Basic
    @Column(name = "task_list_type", nullable = false)
    short taskListType

    @Basic
    @Column(name = "task_list_index", nullable = true, length = 255)
    int taskListIndex

    @Basic
    @Column(name = "project_template_id", nullable = false)
    long projectTemplateId

}
