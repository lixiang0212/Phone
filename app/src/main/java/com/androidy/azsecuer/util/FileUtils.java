package com.androidy.azsecuer.util;

import java.io.File;

public class FileUtils {

    public static void delFile(File file) {
        if (file != null) {
            if (!file.isDirectory()) {file.delete();}
            else {
                File files[] = file.listFiles();
                if (files != null) {
                    // #1 删除当前这个目录里的每个文件[递归]
                    for (int i = 0; i < files.length; i++) {
                        delFile(files[i]);
                    }
                    // #2 删除当前这个目录
                    file.delete();
}}}}

    /**
     * 获取指定文件或目录的大小
     *
     * @param file
     * @return 文件大小(byte)
     */
    public static long getFileSize(File file) {
        long size = 0;
        if (file != null) {
            if (!file.isDirectory()) {
                return file.length();
            }
// 是目录,递归迭加计算目录内每个文件的大小
        File files[] = file.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                 if (files[i].isDirectory()) {
                     size = size + getFileSize(files[i]);
                 }
                 else {
                     size = size + files[i].length();
                 }
            }
        }
        }
        return size;
}
}
