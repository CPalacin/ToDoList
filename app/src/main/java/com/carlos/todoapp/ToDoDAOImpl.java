package com.carlos.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ToDoDAOImpl implements ToDoDAO, Serializable{
    private Context context;
    private DatabaseHelper dbHelper;
    private List<ToDo> toDoList;

    public ToDoDAOImpl(Context context){
        this.context = context;
        this.dbHelper = DatabaseHelper.getInstance(context);
        this.toDoList = getAllDB();
    }

    @Override
    public void insert(ToDo toDo) {
        insertDB(toDo);
        toDoList.add(toDo);
    }

    @Override
    public List<ToDo> getAll() {
        return toDoList;
    }

    @Override
    public void delete(ToDo toDo) {
        deleteDB(toDo);
        toDoList.remove(toDo);
    }

    @Override
    public void update(ToDo toDo) {
        Iterator<ToDo> it = toDoList.iterator();
        boolean found = false;

        while (it.hasNext() && !found){
            ToDo curToDo = it.next();
            if (curToDo.getId().equals(toDo.getId())){
                found = true;
                updateDB(toDo);
            }
        }
    }

    public int updateDB(ToDo toDo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_TODO_TASK, toDo.getTitle());
        values.put(DatabaseHelper.KEY_TODO_NOTE, toDo.getNote());
        values.put(DatabaseHelper.KEY_TODO_PRIORITY, toDo.getPriority());

        // Updating profile picture url for user with that userName
        return db.update(DatabaseHelper.TABLE_TODO, values, DatabaseHelper.KEY_TODO_ID + " = ?",
                new String[] { String.valueOf(toDo.getId()) });
    }

    private void insertDB(ToDo toDo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.KEY_TODO_TASK, toDo.getTitle());
            values.put(DatabaseHelper.KEY_TODO_NOTE, toDo.getNote());
            values.put(DatabaseHelper.KEY_TODO_PRIORITY, toDo.getPriority());

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(DatabaseHelper.TABLE_TODO, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(getClass().getSimpleName(), "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    private List<ToDo> getAllDB() {
        List<ToDo> toDoList = new ArrayList<>();

        String select = String.format("SELECT * FROM %s", DatabaseHelper.TABLE_TODO);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    ToDo toDo = new ToDo();
                    toDo.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_TODO_ID)));
                    toDo.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_TODO_TASK)));
                    toDo.setNote(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_TODO_NOTE)));
                    toDo.setPriority(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_TODO_PRIORITY)));
                    toDoList.add(toDo);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(getClass().getSimpleName(), "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return toDoList;
    }

    private void deleteDB(ToDo toDo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            String[] whereArg = new String[] { String.valueOf(toDo.getId()) };
            db.delete(DatabaseHelper.TABLE_TODO, DatabaseHelper.KEY_TODO_ID + "= ?", whereArg);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(getClass().getSimpleName(), "Error while trying to delete");
        } finally {
            db.endTransaction();
        }
    }
}
