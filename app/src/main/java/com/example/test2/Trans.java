package com.example.test2;

import android.content.Context;

public class Trans {
    private String before;
    private String after;
    private int id;
    public Trans(int id){
        this.id=id;
    }
    Trans(int id, String before, String after){
        this.id=id;
        this.after=after;
        this.before=before;
    }
    String getAfter(){
        return after;
    }
    String getBefore(){
        return before;
    }
}

