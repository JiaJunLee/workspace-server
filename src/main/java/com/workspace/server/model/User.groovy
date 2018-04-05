package com.workspace.server.model

import javax.persistence.Basic
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

/**
 * Created by 51998 on 2017/12/30.
 */
@Entity
@Table(name = "ws_user")
class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id

    @Basic
    @Column(name = "username")
    String username

    @Basic
    @Column(name = "password")
    String password

}
