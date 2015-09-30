package com.carlos.todoapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ToDoListAdapter extends ArrayAdapter<ToDo> {
    private final Activity context;
    private LayoutInflater inflater;
    List<ToDo> toDoList;

    public ToDoListAdapter(Activity context, List<ToDo> toDoList) {
        super(context, R.layout.todo_list, toDoList);
        this.context = context;
        this.inflater = context.getLayoutInflater();
        this.toDoList = toDoList;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.todo_list, parent, false);
        // Setting the name of the item
        TextView text = (TextView) view.findViewById(R.id.item_txt);
        text.setText(toDoList.get(position).getTitle());
        return view;
    }
}

