package com.carlos.todoapp.fragment;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.carlos.todoapp.R;
import com.carlos.todoapp.ToDo;
import com.carlos.todoapp.ToDoDAO;
import com.carlos.todoapp.ToDoListAdapter;
import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.getbase.floatingactionbutton.AddFloatingActionButton;


public class TodoList extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private static final String TODO_DAO = "ToDoDAO";
    private static final String NEW_TASK_LISTENER = "newTaskListener";

	private ToDoListAdapter adapter = null;
    private SwipeListView swipeListView;
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

        swipeListView = (SwipeListView) rootView.findViewById(R.id.todo_list_view);
        adapter = new ToDoListAdapter(getActivity(), toDoDAO.getAll());

        swipeListView.setSwipeListViewListener(new BaseSwipeListViewListener() {
            @Override
            public void onOpened(int position, boolean toRight) {
                toDoDAO.delete(adapter.getItem(position));
                swipeListView.dismiss(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(),String.format("onOpened %d", position),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClosed(int position, boolean fromRight) {
                Toast.makeText(getActivity(),String.format("onClosed %d", position),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onListChanged() {
            }

            @Override
            public void onMove(int position, float x) {
            }

            @Override
            public void onStartOpen(int position, int action, boolean right) {
                Toast.makeText(getActivity(),String.format("onStartOpen %d", position),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartClose(int position, boolean right) {
                Toast.makeText(getActivity(),String.format("onClickFrontView %d", position),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClickFrontView(int position) {
                Toast.makeText(getActivity(),String.format("onClickFrontView %d", position),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClickBackView(int position) {
                Log.d("swipe", String.format("onClickBackView %d", position));
                Toast.makeText(getActivity(),String.format("onClickBackView %d", position),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {
                Toast.makeText(getActivity(),"deleting",Toast.LENGTH_SHORT).show();
                for (int position : reverseSortedPositions) {
                }
                adapter.notifyDataSetChanged();
            }

        });

        swipeListView.setAdapter(adapter);
        swipeListView.setOnItemClickListener(this);
        AddFloatingActionButton addButton = (AddFloatingActionButton) rootView.findViewById(R.id.normal_plus);
        addButton.setOnClickListener(this);
		return rootView;
	}

    public void setNewTaskListener(NewTaskListener newTaskListener){
        this.newTaskListener = newTaskListener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        newTaskListener.updateTask(adapter.getItem(position));
    }

    @Override
    public void onClick(View v) {
        newTaskListener.createTask();
    }

    public interface NewTaskListener{
        public void createTask();
        public void updateTask(ToDo toDo);
    }
}