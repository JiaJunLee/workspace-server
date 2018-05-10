package com.workspace.server.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Entity
@Table(name = "project", schema = "workspace_db", catalog = "")
@EntityListeners(AuditingEntityListener.class)
class ProjectEntity {

    public static final Integer VISIBILITY_PUBLIC = 0
    public static final Integer VISIBILITY_PRIVATE = 1
    public static final Integer VISIBILITY_DISABLE = 2

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    String name

    @Basic
    @Column(name = "introduce", nullable = true, length = 511)
    String introduce

    @Basic
    @Column(name = "visibility", nullable = false)
    short visibility

    @Basic
    @Column(name = "icon_file_id", nullable = true)
    Long iconFileId

    @Basic
    @Column(name = "team_id", nullable = true)
    Long teamId

    @Basic
    @Column(name = "owner_user_id", nullable = true)
    Long ownerUserId

    @Basic
    @Column(name = "create_datetime", nullable = false)
    @CreatedDate
    Date createDatetime

}
