package com.carlos.todoapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ToDoDAOImpl implements ToDoDAO, Serializable{

    private List<ToDo> toDoList = new ArrayList<>();

    @Override
    public void insert(ToDo toDo) {
        toDoList.add(toDo);
    }

    @Override
    public List<ToDo> getAll() {
        return toDoList;
    }

    @Override
    public void delete(long id) {
    }

    @Override
    public void update(ToDo toDo) {

    }
}
