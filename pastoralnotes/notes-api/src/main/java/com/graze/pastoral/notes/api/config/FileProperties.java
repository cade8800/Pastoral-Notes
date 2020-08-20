package com.graze.pastoral.notes.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/6/17 10:55
 */
@ConfigurationProperties(prefix = "file")
public class FileProperties {
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
