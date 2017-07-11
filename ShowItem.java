package com.example.administrator.testall.entity;

/**
 * Created by Administrator on 2017/6/27.
 */

public class ShowItem {
    private String name;
    private int     type;   //0~3

    public ShowItem(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }
}
