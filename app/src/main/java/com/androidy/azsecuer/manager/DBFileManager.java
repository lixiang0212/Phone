package com.androidy.azsecuer.manager;

import android.content.Context;
import android.util.Log;
import com.androidy.azsecuer.config.DBFileconfig;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//用来拷贝文件到数据库下。
public class DBFileManager {
    //1判断要拷贝的文件是否存在
    private static boolean isExists(String file) {
        File path = DBFileconfig.path;
        File toFile = new File(path, file);//读取文件
        return toFile.exists();//判断读取文件是否存在
    }
    //2拷贝文件fileName-目标文件，tofileName-生成文件
    public static void copyFile(Context context,String fileName,String tofileName ) throws IOException {

        if (isExists(tofileName)) {return;}

        InputStream is =context.getResources().getAssets().open(fileName);//读取assets下的文件
        BufferedInputStream bis = new BufferedInputStream(is);//节约时间
        File toFileDir =DBFileconfig.path;//判断文件路径是否存在，不存在则创建

        if (!toFileDir.exists()) {toFileDir.mkdirs();}

        File toFile = new File(toFileDir, tofileName);//将数据写入目标文件
        OutputStream os = new FileOutputStream(toFile);
        BufferedOutputStream bos = new BufferedOutputStream(os);

        byte data[] = new byte[1024];
        int len = 0;
        while ((len=bis.read(data))!=-1){
            bos.write(data,0,len);
        }
        bos.flush();os.flush();is.close();bis.close();bos.close();os.close();
    }
}
