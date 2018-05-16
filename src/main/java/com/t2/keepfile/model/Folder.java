package com.t2.keepfile.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

//@Entity
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String folderName;

    private String description;

    private String folderPath;

    private String folderLocation;

    //in kb
    private double size;

    private Date createTime;

    private Date lasModified;

    private boolean isTrash;

//    private long parentId;
//
//    private long ownerId;

    @OneToOne
    @JoinColumn(name = "parent_id")
    private Folder parent;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private Account owner;

    @OneToMany(mappedBy = "folder")
    private List<File> files;

    public Folder() {
    }

    public Folder(String folderName, String description, String folderPath, String folderLocation, double size, Date createTime, Date lasModified, boolean isTrash) {
        this.folderName = folderName;
        this.description = description;
        this.folderPath = folderPath;
        this.folderLocation = folderLocation;
        this.size = size;
        this.createTime = createTime;
        this.lasModified = lasModified;
        this.isTrash = isTrash;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getFolderLocation() {
        return folderLocation;
    }

    public void setFolderLocation(String folderLocation) {
        this.folderLocation = folderLocation;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLasModified() {
        return lasModified;
    }

    public void setLasModified(Date lasModified) {
        this.lasModified = lasModified;
    }

    public boolean isTrash() {
        return isTrash;
    }

    public void setTrash(boolean trash) {
        isTrash = trash;
    }

//    public long getParentId() {
//        return parentId;
//    }
//
//    public void setParentId(long parentId) {
//        this.parentId = parentId;
//    }
//
//    public long getOwnerId() {
//        return ownerId;
//    }
//
//    public void setOwnerId(long ownerId) {
//        this.ownerId = ownerId;
//    }

    public Folder getParent() {
        return parent;
    }

    public void setParent(Folder parent) {
        this.parent = parent;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
