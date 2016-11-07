package com.androidy.azsecuer.util;

public class TelNumberInfo {

    public String name;
    public String number;

    public TelNumberInfo(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public String toString() {
        return "TelNumberInfo{"+"name='"+name+'\''+",number='"+number+'\''+'}';
    }
}
