package com.androidy.azsecuer.manager;


import com.androidy.azsecuer.util.FileManagerInfo;

import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private  List<FileManagerInfo> listTxt = new ArrayList<>();
    private  List<FileManagerInfo> listPng = new ArrayList<>();
    private  List<FileManagerInfo> listApk = new ArrayList<>();
    private  List<FileManagerInfo> listMp4 = new ArrayList<>();
    private  List<FileManagerInfo> listMp3 = new ArrayList<>();
    private  List<FileManagerInfo> listZip = new ArrayList<>();
    private  List<FileManagerInfo> listOther = new ArrayList<>();

    private static FileManager manager = new FileManager();
    public static FileManager getManager(){
        if(manager==null){
            manager=new FileManager();
        }
        return manager;
    }

    public   void setListTxt(List<FileManagerInfo> list){
        listTxt=list;
    }
    public  void setListPng(List<FileManagerInfo> list){
        listPng=list;
    }
    public  void setListApk(List<FileManagerInfo> list){
        listApk=list;
    }
    public  void setListMp4(List<FileManagerInfo> list){
        listMp4=list;
    }
    public  void setListMp3(List<FileManagerInfo> list){
        listMp3=list;
    }
    public  void setListZip(List<FileManagerInfo> list){
        listZip=list;
    }
    public  void setListOther(List<FileManagerInfo> list){
        listOther=list;
    }
    public  List<FileManagerInfo> getTxt(){
        return listTxt;
    }
    public  List<FileManagerInfo> getPng(){
        return listPng;
    }
    public  List<FileManagerInfo> getApk(){
        return listApk;
    }
    public  List<FileManagerInfo> getMp4(){
        return listMp4;
    }
    public  List<FileManagerInfo> getMp3(){
        return listMp3;
    }
    public  List<FileManagerInfo> getZip(){
        return listZip;
    }
    public  List<FileManagerInfo> getOther(){
        return listOther;
    }






}
