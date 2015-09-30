package com.carlos.todoapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.carlos.todoapp.R;
import com.carlos.todoapp.ToDoDAO;
import com.carlos.todoapp.ToDoListAdapter;
import com.getbase.floatingactionbutton.AddFloatingActionButton;


public class TodoList extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private static final String TODO_DAO = "ToDoDAO";
    private static final String NEW_TASK_LISTENER = "newTaskListener";

	private ToDoListAdapter adapter = null;
    private ListView list;
    private ToDoDAO toDoDAO;
    private NewTaskListener newTaskListener;

    public static TodoList newInstance(ToDoDAO ToDoDAO) {
        TodoList f = new TodoList();
        Bundle args = new Bundle();

        args.putSerializable(TODO_DAO, ToDoDAO);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        toDoDAO = (ToDoDAO) getArguments().getSerializable(TODO_DAO);
    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_todo_list, container, false);

        adapter = new ToDoListAdapter(getActivity(), toDoDAO.getAll());
	    list= (ListView) rootView.findViewById(R.id.todo_list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);

        AddFloatingActionButton addButton = (AddFloatingActionButton) rootView.findViewById(R.id.normal_plus);
        addButton.setOnClickListener(this);
		return rootView;
	}

    public void setNewTaskListener(NewTaskListener newTaskListener){
        this.newTaskListener = newTaskListener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Pressed item", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        newTaskListener.createTask();
    }

    public interface NewTaskListener{
        public void createTask();
    }
}