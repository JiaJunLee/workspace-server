package com.workspace.server.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "team", schema = "workspace_db", catalog = "")
@EntityListeners(AuditingEntityListener.class)
public class TeamEntity {
    private long id;
    private String name;
    private String introduce;
    private Date createDatetime;
    private long administratorUserId;
    private Long iconFileId;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "introduce", nullable = true, length = 511)
    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Basic
    @Column(name = "create_datetime", nullable = false)
    @CreatedDate
    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    @Basic
    @Column(name = "administrator_user_id", nullable = false)
    public long getAdministratorUserId() {
        return administratorUserId;
    }

    public void setAdministratorUserId(long administratorUserId) {
        this.administratorUserId = administratorUserId;
    }

    @Basic
    @Column(name = "icon_file_id", nullable = true)
    public Long getIconFileId() {
        return iconFileId;
    }

    public void setIconFileId(Long iconFileId) {
        this.iconFileId = iconFileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamEntity that = (TeamEntity) o;
        return id == that.id &&
                administratorUserId == that.administratorUserId &&
                Objects.equals(name, that.name) &&
                Objects.equals(introduce, that.introduce) &&
                Objects.equals(createDatetime, that.createDatetime) &&
                Objects.equals(iconFileId, that.iconFileId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, introduce, createDatetime, administratorUserId, iconFileId);
    }
}
