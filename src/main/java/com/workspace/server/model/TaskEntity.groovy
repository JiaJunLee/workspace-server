package com.workspace.server.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

import javax.persistence.*
@Entity
@Table(name = "task", schema = "workspace_db", catalog = "")
@EntityListeners(AuditingEntityListener.class)
class TaskEntity {

    public static final Integer TYPE_NORMAL = 0
    public static final Integer TYPE_WARNING = 1
    public static final Integer TYPE_PRIMARY = 2

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id

    @Basic
    @Column(name = "type", nullable = false)
    short type

    @Basic
    @Column(name = "content", nullable = false, length = 1023)
    String content

    @Basic
    @Column(name = "expiry_datetime", nullable = true)
    Date expiryDatetime

    @Basic
    @Column(name = "task_list_id", nullable = false)
    long taskListId

    @Basic
    @Column(name = "owner_user_id", nullable = false)
    long ownerUserId

    @Basic
    @Column(name = "create_datetime", nullable = false)
    @CreatedDate
    Date createDatetime

}
