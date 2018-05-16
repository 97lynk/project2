package com.t2.keepfile.model;

import javax.persistence.*;
import java.util.Date;

//@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userName;

    private String password;

    private boolean activited;

    private Date createTime;

    @OneToOne
    private Profile profile;

    public Account() {
    }

    public Account(String userName, String password, boolean activited, Date createTime) {
        this.userName = userName;
        this.password = password;
        this.activited = activited;
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActivited() {
        return activited;
    }

    public void setActivited(boolean activited) {
        this.activited = activited;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
