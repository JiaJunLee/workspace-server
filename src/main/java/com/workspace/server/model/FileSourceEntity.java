package com.workspace.server.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "file_source", schema = "workspace_db", catalog = "")
@EntityListeners(AuditingEntityListener.class)
public class FileSourceEntity {
    private long id;
    private String name;
    private String md5;
    private String type;
    private String size;
    private Date createDatetime;

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
    @Column(name = "md5", nullable = false, length = 255)
    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Basic
    @Column(name = "type", nullable = false, length = 255)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "size", nullable = false, length = -1)
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileSourceEntity that = (FileSourceEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(md5, that.md5) &&
                Objects.equals(type, that.type) &&
                Objects.equals(size, that.size) &&
                Objects.equals(createDatetime, that.createDatetime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, md5, type, size, createDatetime);
    }
}
