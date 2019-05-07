package com.wnliam.uqdate.info;

import java.io.Serializable;

/**
 * 文件信息
 */
public class FileInfo implements Serializable {
    private String name;
    private String time;
    private Double size;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }



}
