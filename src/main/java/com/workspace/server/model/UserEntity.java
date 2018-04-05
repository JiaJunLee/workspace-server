package com.workspace.server.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by 51998 on 2018/2/26.
 */
@Entity
@Table(name = "user", schema = "workspace_db", catalog = "")
@IdClass(UserEntityPK.class)
public class UserEntity {
    private long id;
    private String email;
    private String hsPassword;
    private String hsKey;
    private String name;
    private String phone;
    private short sex;
    private Date birth;
    private String address;
    private Timestamp createDatetime;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Id
    @Column(name = "email", nullable = false, length = 127)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "hs_password", nullable = false, length = 127)
    public String getHsPassword() {
        return hsPassword;
    }

    public void setHsPassword(String hsPassword) {
        this.hsPassword = hsPassword;
    }

    @Basic
    @Column(name = "hs_key", nullable = false, length = 127)
    public String getHsKey() {
        return hsKey;
    }

    public void setHsKey(String hsKey) {
        this.hsKey = hsKey;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 31)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "phone", nullable = true, length = 31)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "sex", nullable = false)
    public short getSex() {
        return sex;
    }

    public void setSex(short sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "birth", nullable = true)
    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Basic
    @Column(name = "address", nullable = true, length = 255)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "create_datetime", nullable = false)
    public Timestamp getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Timestamp createDatetime) {
        this.createDatetime = createDatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (sex != that.sex) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (hsPassword != null ? !hsPassword.equals(that.hsPassword) : that.hsPassword != null) return false;
        if (hsKey != null ? !hsKey.equals(that.hsKey) : that.hsKey != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (birth != null ? !birth.equals(that.birth) : that.birth != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (createDatetime != null ? !createDatetime.equals(that.createDatetime) : that.createDatetime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (hsPassword != null ? hsPassword.hashCode() : 0);
        result = 31 * result + (hsKey != null ? hsKey.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (int) sex;
        result = 31 * result + (birth != null ? birth.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (createDatetime != null ? createDatetime.hashCode() : 0);
        return result;
    }
}
