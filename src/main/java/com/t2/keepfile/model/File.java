package com.t2.keepfile.model;

import javax.persistence.*;
import java.util.Date;

//@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String filerName;

    private String description;

    private String filePath;

    private String fileLocation;

    //in kb
    private double size;

    private Date createTime;

    private Date lasModified;

    private boolean isTrash;

 //   private long folderId;

   // private long ownerId;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private Account owner;

    @ManyToOne
    private Folder folder;

    public File() {
    }

    public File(String filerName, String description, String filePath, String fileLocation, double size, Date createTime, Date lasModified, boolean isTrash, Account owner, Folder folder) {
        this.filerName = filerName;
        this.description = description;
        this.filePath = filePath;
        this.fileLocation = fileLocation;
        this.size = size;
        this.createTime = createTime;
        this.lasModified = lasModified;
        this.isTrash = isTrash;
        this.owner = owner;
        this.folder = folder;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilerName() {
        return filerName;
    }

    public void setFilerName(String filerName) {
        this.filerName = filerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
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


//    public long getFolderId() {
//        return folderId;
//    }
//
//    public void setFolderId(long folderId) {
//        this.folderId = folderId;
//    }

//    public long getOwnerId() {
//        return ownerId;
//    }
//
//    public void setOwnerId(long ownerId) {
//        this.ownerId = ownerId;
//    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }
}
