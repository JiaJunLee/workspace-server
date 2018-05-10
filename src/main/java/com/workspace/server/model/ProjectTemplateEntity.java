package com.workspace.server.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "project_template", schema = "workspace_db", catalog = "")
@EntityListeners(AuditingEntityListener.class)
public class ProjectTemplateEntity {
    private int id;
    private String name;
    private String introduce;
    private Date createDatetime;
    private long iconFileId;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    @Column(name = "introduce", nullable = false, length = 511)
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
    @Column(name = "icon_file_id", nullable = false)
    public long getIconFileId() {
        return iconFileId;
    }

    public void setIconFileId(long iconFileId) {
        this.iconFileId = iconFileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectTemplateEntity that = (ProjectTemplateEntity) o;
        return id == that.id &&
                iconFileId == that.iconFileId &&
                Objects.equals(name, that.name) &&
                Objects.equals(introduce, that.introduce) &&
                Objects.equals(createDatetime, that.createDatetime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, introduce, createDatetime, iconFileId);
    }
}
