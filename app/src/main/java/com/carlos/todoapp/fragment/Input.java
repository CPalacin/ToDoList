package com.carlos.todoapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.carlos.todoapp.R;
import com.carlos.todoapp.ToDo;
import com.carlos.todoapp.ToDoDAO;
import com.carlos.todoapp.ToDoListAdapter;

/**
 * Created by carlos on 9/29/2015.
 */
public class Input extends Fragment{
    private static final String TODO_DAO = "ToDoDAO";
    private static final String TODO = "ToDo";
    private ToDoDAO toDoDAO;
    private ToDo toDo;

    /**
     * Creates a fragment able to create a new task.
     */
    public static Input newInstance(ToDoDAO toDoDAO) {
        return newInstance(toDoDAO, null);
    }

    /**
     * Creates a fragment able to update one task of the the list.
     */
    public static Input newInstance(ToDoDAO toDoDAO, ToDo todo) {
        Input f = new Input();
        Bundle args = new Bundle();

        args.putSerializable(TODO_DAO, toDoDAO);
        if(todo != null){
            args.putSerializable(TODO, todo);
        }
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        toDoDAO = (ToDoDAO) getArguments().getSerializable(TODO_DAO);
        toDo = (ToDo) getArguments().getSerializable(TODO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_input, container, false);

        EditText taskName = (EditText) rootView.findViewById(R.id.taskName);
        if(toDo != null) {
            taskName.setText(toDo.getTitle());
        }

        return rootView;
    }

}
