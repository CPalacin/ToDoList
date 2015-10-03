package com.carlos.todoapp.fragment;

import android.app.Fragment;
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
import com.carlos.todoapp.ToDoDAOImpl;
import com.carlos.todoapp.ToDoListAdapter;
import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.getbase.floatingactionbutton.AddFloatingActionButton;


public class TodoList extends Fragment implements View.OnClickListener {
	private ToDoListAdapter adapter = null;
    private SwipeListView swipeListView;

    transient private ToDoDAO toDoDAO;
    private NewTaskListener newTaskListener;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_todo_list, container, false);

        swipeListView = (SwipeListView) rootView.findViewById(R.id.todo_list_view);
        adapter = new ToDoListAdapter(getActivity(), getToDoDAO().getAll());

        swipeListView.setSwipeListViewListener(new BaseSwipeListViewListener() {
            @Override
            public void onOpened(int position, boolean toRight) {
                getToDoDAO().delete(adapter.getItem(position));
                swipeListView.dismiss(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onClosed(int position, boolean fromRight) {
            }

            @Override
            public void onListChanged() {
            }

            @Override
            public void onMove(int position, float x) {
            }

            @Override
            public void onStartOpen(int position, int action, boolean right) {
            }

            @Override
            public void onStartClose(int position, boolean right) {
            }

            @Override
            public void onClickFrontView(int position) {
                newTaskListener.updateTask(adapter.getItem(position));
            }

            @Override
            public void onClickBackView(int position) {
            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {
            }

        });

        swipeListView.setAdapter(adapter);
        AddFloatingActionButton addButton = (AddFloatingActionButton) rootView.findViewById(R.id.normal_plus);
        addButton.setOnClickListener(this);
		return rootView;
	}

    public void setNewTaskListener(NewTaskListener newTaskListener){
        this.newTaskListener = newTaskListener;
    }

    @Override
    public void onClick(View v) {
        newTaskListener.createTask();
    }

    private ToDoDAO getToDoDAO(){
        if(toDoDAO == null){
            toDoDAO = new ToDoDAOImpl(getActivity());
        }

        return toDoDAO;
    }

    public interface NewTaskListener{
        public void createTask();
        public void updateTask(ToDo toDo);
    }
}