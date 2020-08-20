package com.graze.pastoral.notes.domain.file;

import lombok.Data;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/20 12:21
 */

@Data
public class UploadFileResponse {
    private String fileName;
    private String fileUri;
    private String fileType;
    private long size;

    public UploadFileResponse(String fileName, String fileUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileUri = fileUri;
        this.fileType = fileType;
        this.size = size;
    }
}
