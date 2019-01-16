package com.wnliam.uqdate.info;

/**
 * 文件信息
 */
public class FileInfo {

    private String path;

    public FileInfo(String path) {
        this.path = path;
    }

    public FileInfo() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
