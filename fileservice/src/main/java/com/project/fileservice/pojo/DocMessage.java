package com.project.fileservice.pojo;

public class DocMessage {
    //标题
    private String strTitle;
    //关键字
    private String strKey;
    //内容
    private String strMessage;
    //地址
    private String strPath;
    public DocMessage() {

    }
    public DocMessage(String strTitle, String strKey, String strMessage, String strPath) {
        this.strTitle = strTitle;
        this.strKey = strKey;
        this.strMessage = strMessage;
        this.strPath = strPath;
    }

    public String getStrPath() {
        return strPath;
    }
    public void setStrPath(String strPath) {
        this.strPath = strPath;
    }
    public String getStrTitle() {
        return strTitle;
    }

    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }

    public String getStrKey() {
        return strKey;
    }

    public void setStrKey(String strKey) {
        this.strKey = strKey;
    }

    public String getStrMessage() {
        return strMessage;
    }

    public void setStrMessage(String strMessage) {
        this.strMessage = strMessage;
    }


}
