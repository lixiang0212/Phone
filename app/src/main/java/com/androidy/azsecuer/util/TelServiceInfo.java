package com.androidy.azsecuer.util;

public class TelServiceInfo {

    public String name;
    public int idx;

    public TelServiceInfo(int idx, String name) {
        this.idx = idx;
        this.name = name;
    }

    public String toString() {
        return "TelServiceInfo{"+"name='"+name+'\''+",idx="+idx+'}';
    }
}
