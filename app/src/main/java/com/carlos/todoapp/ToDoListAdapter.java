package com.carlos.todoapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fortysevendeg.swipelistview.SwipeListView;

import java.util.List;

public class ToDoListAdapter extends ArrayAdapter<ToDo> {
    private final Activity context;
    private LayoutInflater inflater;
    private List<ToDo> toDoList;

    public ToDoListAdapter(Activity context, List<ToDo> toDoList) {
        super(context, R.layout.package_row, toDoList);
        this.context = context;
        this.inflater = context.getLayoutInflater();
        this.toDoList = toDoList;
    }

    @Override
    public int getCount() {
        return toDoList.size();
    }

    @Override
    public ToDo getItem(int position) {
        return toDoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ToDo item = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.package_row, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.item_txt);
            Assets.setFontRoboto(holder.title, getContext());

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ((SwipeListView)parent).recycle(convertView, position);

        holder.title.setText(item.getTitle());

        return convertView;
    }

    static class ViewHolder {
        Spinner priority;
        TextView title;
        TextView note;
    }
}

