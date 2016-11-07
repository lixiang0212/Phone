package com.androidy.azsecuer.util;

public class User {
    public String name;
    public String passwd;
    public long id;

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String name, String passwd, long id) {
        this.name = name;
        this.passwd = passwd;
        this.id = id;
    }

    public User(String name, String passwd) {
        this.name = name;
        this.passwd = passwd;
    }

    public String toString() {
        return "User{"+"name='"+name+'\''+",passwd='"+passwd+'\''+",id="+id+'}';
    }
}
