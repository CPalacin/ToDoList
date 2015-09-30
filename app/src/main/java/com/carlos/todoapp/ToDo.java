package com.carlos.todoapp;

import java.io.Serializable;

public class ToDo implements Serializable{

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ToDo(String title) {
        this.title = title;
    }
}
