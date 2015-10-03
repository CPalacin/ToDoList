package com.carlos.todoapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.carlos.todoapp.R;
import com.carlos.todoapp.ToDo;
import com.carlos.todoapp.ToDoDAO;
import com.carlos.todoapp.ToDoDAOImpl;

public class Input extends Fragment implements TextWatcher, AdapterView.OnItemSelectedListener {
    private static final String TODO = "ToDo";
    private transient ToDoDAO toDoDAO;
    private ToDo toDo;
    private boolean insert = false;
    private EditText taskName;
    private EditText note;
    private Spinner priority;

    /**
     * Creates a fragment able to update one task of the the list.
     */
    public static Input newInstance(ToDo todo) {
        Input f = new Input();
        Bundle args = new Bundle();
        args.putSerializable(TODO, todo);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        if(getArguments() != null) {
            toDo = (ToDo) getArguments().getSerializable(TODO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_input, container, false);

        taskName = (EditText) rootView.findViewById(R.id.taskName);
        note = (EditText) rootView.findViewById(R.id.taskNote);
        priority = (Spinner) rootView.findViewById(R.id.spinner);
        if(toDo != null) {
            taskName.setText(toDo.getTitle());
            note.setText(toDo.getNote());
            System.out.println(toDo.getPriority());
            if(toDo.getPriority().equalsIgnoreCase("low")){
                priority.setSelection(0);
            } else if(toDo.getPriority().equalsIgnoreCase("medium")){
                priority.setSelection(1);
            }else{
                priority.setSelection(2);
            }
        }else{
            toDo = new ToDo();
            insert = true;
        }

        taskName.addTextChangedListener(this);
        note.addTextChangedListener(this);
        priority.setOnItemSelectedListener(this);
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
        toDo.setTitle(taskName.getText().toString());
        toDo.setNote(note.getText().toString());
    }

    @Override
    public void onPause() {
        persistTask();
        super.onPause();
    }

    private ToDoDAO getToDoDAO(){
        if(toDoDAO == null){
            toDoDAO = new ToDoDAOImpl(getActivity());
        }

        return toDoDAO;
    }

    public void persistTask(){
        if(insert){
            getToDoDAO().insert(toDo);
        }else{
            getToDoDAO().update(toDo);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        toDo.setPriority(priority.getSelectedItem().toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
