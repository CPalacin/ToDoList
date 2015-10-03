package com.carlos.todoapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.carlos.todoapp.fragment.Input;
import com.carlos.todoapp.fragment.TodoList;

public class MainActivity extends AppCompatActivity implements TodoList.NewTaskListener {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbarCreation();
        switchToToDoListFragment();
    }

    private void switchToToDoListFragment() {
        setToolbarTitle("To Do List");
        TodoList toDoListFragment = new TodoList();
        toDoListFragment.setNewTaskListener(this);
        switchFragment(toDoListFragment);
    }

    private void switchFragment(Fragment fragment){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void setToolbarTitle(String title) {
        if (toolbar != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            Assets.setFontRoboto(toolbarTitle, this);
            toolbarTitle.setText(title);
        }
    }

    private void toolbarCreation() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.getMenu().clear();
        setSupportActionBar(toolbar);
    }

    public void setHomeButtonVisibility(boolean visibility) {
        getSupportActionBar().setHomeButtonEnabled(visibility);
        getSupportActionBar().setDisplayHomeAsUpEnabled(visibility);
    }

    @Override
    public void createTask() {
        setHomeButtonVisibility(true);
        Input inputFragment = new Input();
        switchFragment(inputFragment);
    }

    @Override
    public void updateTask(ToDo toDo) {
        setHomeButtonVisibility(true);
        Input inputFragment = Input.newInstance(toDo);
        switchFragment(inputFragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            switchToToDoListFragment();
        }

        setHomeButtonVisibility(false);
        return super.onOptionsItemSelected(item);
    }
}
