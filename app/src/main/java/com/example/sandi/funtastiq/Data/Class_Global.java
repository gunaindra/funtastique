package com.example.sandi.funtastiq.Data;

/**
 * Created by SandI on 12/13/2016.
 */

public class Class_Global {
    private static Class_Global instance;
    private String user;

    private Class_Global() {
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String username) {
        this.user = username;
    }

    public static synchronized Class_Global getInstance(){
        if (instance == null){
            instance=new Class_Global();
        }
        return instance;
    }
}
