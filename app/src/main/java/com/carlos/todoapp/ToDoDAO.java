package com.carlos.todoapp;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by carlos on 9/28/2015.
 */
public interface ToDoDAO extends Serializable{
    public void insert(ToDo toDo);
    public List<ToDo> getAll();
    public void delete(ToDo toDo);
    public void update(ToDo toDo);
}
