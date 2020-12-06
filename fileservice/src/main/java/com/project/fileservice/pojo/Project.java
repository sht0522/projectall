package com.project.fileservice.pojo;

import java.io.Serializable;

public class Project implements Serializable {
    private static final long serialVersionUID = 1L;
    //ID
    private Integer nId;
    //项目空间
    private String strProjectSpace;
    //项目名称
    private String strProjectName;
    //父级项目空间
    private Integer nParentSpace;

    public Integer getnId() {
        return nId;
    }

    public void setnId(Integer nId) {
        this.nId = nId;
    }

    public String getStrProjectSpace() {
        return strProjectSpace;
    }

    public void setStrProjectSpace(String strProjectSpace) {
        this.strProjectSpace = strProjectSpace;
    }

    public String getStrProjectName() {
        return strProjectName;
    }

    public void setStrProjectName(String strProjectName) {
        this.strProjectName = strProjectName;
    }

    public Integer getnParentSpace() {
        return nParentSpace;
    }

    public void setnParentSpace(Integer nParentSpace) {
        this.nParentSpace = nParentSpace;
    }
}
