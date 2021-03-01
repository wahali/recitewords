package com.eng.recitewords.entity;

/**
* 上传响应类
 * */
public class UploadFile {

    private long fileId;
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
    private int checked;

    public UploadFile(long fileId, String fileName, String fileDownloadUri, String fileType, long size, int checked) {
//        this.fileId = fileId;
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
//        this.checked = checked;
    }

    public UploadFile(String fileName) {
        this.fileName = fileName;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

}
