package com.carlos.todoapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.carlos.todoapp.R;
import com.carlos.todoapp.ToDo;
import com.carlos.todoapp.ToDoDAO;

public class Input extends Fragment implements TextWatcher {
    private static final String TODO_DAO = "ToDoDAO";
    private static final String TODO = "ToDo";
    private ToDoDAO toDoDAO;
    private ToDo toDo;
    private boolean insert = false;

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
        }else{
            toDo = new ToDo();
            insert = true;
        }

        taskName.addTextChangedListener(this);

        return rootView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        toDo.setTitle(s.toString());
    }

    @Override
    public void onPause() {
        persistTask();
        super.onPause();
    }

    public void persistTask(){
        if(insert){
            toDoDAO.insert(toDo);
        }else{
            toDoDAO.update(toDo);
        }
    }
}
