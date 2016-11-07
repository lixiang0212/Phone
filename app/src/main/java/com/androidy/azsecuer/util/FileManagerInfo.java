package com.androidy.azsecuer.util;

import java.io.File;

public class FileManagerInfo {

    public String fileKind;
    public long   fileSize;
    public File file;

    public FileManagerInfo(String fileKind, long fileSize, File file) {
        this.fileKind = fileKind;
        this.fileSize = fileSize;
        this.file = file;
    }

    public String toString() {
        return "FileManagerInfo{" + "fileKind='" + fileKind + '\'' + '}';
    }
}
