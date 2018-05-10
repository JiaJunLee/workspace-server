package com.workspace.server.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
class UserEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id

    @Basic
    @Column(name = "email")
    String email

    @Basic
    @Column(name = "address")
    String address

    @Basic
    @Column(name = "birth")
    Date birth

    @Basic
    @Column(name = "create_datetime")
    @CreatedDate
    Date createDatetime

    @Basic
    @Column(name = "hs_key")
    String hsKey

    @Basic
    @Column(name = "hs_password")
    String hsPassword

    @Basic
    @Column(name = "name")
    String name

    @Basic
    @Column(name = "phone")
    String phone

    @Basic
    @Column(name = "sex")
    short sex

    @Basic
    @Column(name = "icon_file_id")
    Long iconFileId

}
