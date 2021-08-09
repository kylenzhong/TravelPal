package com.example.travelapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.travelapp.Activities.MainActivity;
import com.example.travelapp.Classes.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDBHelper extends SQLiteOpenHelper {

    public static final String TRAVEL_TASK_TABLE = "TRAVEL_TASK_TABLE";
    public static final String COLUMN_ID = "COLUMN_ID";
    public static final String COLUMN_TASKNAME = "COLUMN_TASKNAME";
    public static final String COLUMN_IS_COMPLETED = "COLUMN_IS_COMPLETED";
    public static final String COLUMN_TASKDATE = "COLUMN_TASKDATE";


    public TaskDBHelper(@Nullable Context context) {
        super(context, "travel_task.db", null, 1);
    }

    @Override

    //called once when initially creating a new database.
    public void onCreate(SQLiteDatabase db) {
        //String createTableStatement= "CREATE TABLE TRAVEL_TASK_TABLE (COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, COLUMN_TASKNAME TEXT, COLUMN_TASKDATE TEXT)";
        String createTableStatement = "CREATE TABLE " + TRAVEL_TASK_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TASKNAME + " TEXT, " + COLUMN_IS_COMPLETED + " BOOL, " + COLUMN_TASKDATE + " TEXT)";
        db.execSQL(createTableStatement);
    }


    //database version: when we modify (add or delete) data attributes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //define a method allowing user to add Task to the db => writing into database.
    public boolean addTask(Task taskadded){
        SQLiteDatabase sqldb=this.getWritableDatabase();
        //ContentValues are like hashmap, or Intent.
        ContentValues cv= new ContentValues();
        cv.put(COLUMN_TASKNAME,taskadded.getTaskName());
        cv.put(COLUMN_IS_COMPLETED,taskadded.get_is_completed());
        cv.put(COLUMN_TASKDATE,taskadded.getStringDate());
        long insert = sqldb.insert(TRAVEL_TASK_TABLE, null, cv);
        //use insert to tell if it has been inserted successfully.
        if (insert== -1){
            return false;
        }else {
            return true;
        }
    }


    //define a method for removing task from db
    public boolean removeTask(Task task_to_remove){
        SQLiteDatabase sqldb=this.getWritableDatabase();
        //remove item by their task id.
        String delete_command= "DELETE FROM " + TRAVEL_TASK_TABLE + " WHERE " + COLUMN_ID + " = " + task_to_remove.getTaskId();
        Cursor cursor = sqldb.rawQuery(delete_command, null);
        if (cursor.moveToFirst()){
            //if the there is a return result (cursor)
            return true;
        }else{
            return false;
        }
    }

    //define a method to update the completion status of task in db.
    public void update_completion_status_in_db(int id, boolean current_status){
        SQLiteDatabase sqldb=this.getWritableDatabase();
        String update_command= "UPDATE "+ TRAVEL_TASK_TABLE + " SET " + COLUMN_IS_COMPLETED + " = " + current_status + " WHERE " + COLUMN_ID + " = " + id;
        sqldb.execSQL(update_command);
    }

    public void update_completion_status_in_db(Task task_ToBeEdited){
        SQLiteDatabase sqldb=this.getWritableDatabase();
        int is_completed_in_int;
        if(task_ToBeEdited.get_is_completed()){
            is_completed_in_int=1;
        }else{
            is_completed_in_int=0;
        }
        String update_command= "UPDATE TRAVEL_TASK_TABLE SET COLUMN_IS_COMPLETED = " + is_completed_in_int + " WHERE COLUMN_ID = " + task_ToBeEdited.getTaskId();
        sqldb.execSQL(update_command);
    }



    //define a method to read or retrieve ALL data from db.
    public List<Task> getAllItem(){
        List task_collection= new ArrayList();
        String query_all_string="SELECT * FROM " + TRAVEL_TASK_TABLE;
        SQLiteDatabase db= this.getReadableDatabase();
        //cursor the result.
        Cursor cursor= db.rawQuery(query_all_string,null);
        if(cursor.moveToFirst()){
            //loop through result and create new task obj and add it to the list.
            do{
                //extract out info from cursor or result.
                int task_id= cursor.getInt(0);
                String item_name=cursor.getString(1);
                //no boolean value from database => stored as integer 1 or 0
                //thus need to determine if it's 1 (true) or 0 (false)
                boolean item_completion = cursor.getInt(2) == 1 ? true :false;
                String item_date=cursor.getString(3);
                //use extracted info to create new Task object.
                Task item= new Task(item_name,item_date,task_id,item_completion);
                //add the new task object to the list.
                task_collection.add(item);
            }while(cursor.moveToNext());

        }else{
            //failure to add anything to list.
        }
        cursor.close();
        db.close();
        return task_collection;
    }



    //define a method to read or retrieve filtered data from db.
    public List<Task> getTaskByDate(String date_selected){
        List task_collection= new ArrayList();
        Log.i("TaskDBHelper", "getTaskByDate: "+ date_selected);
        //filter and retrieve task in database by date.
        String query_by_date_command= "SELECT * FROM " + TRAVEL_TASK_TABLE + " WHERE " + COLUMN_TASKDATE + "= '" + date_selected+ "'";
        Log.i("TaskDBHelper", "getTaskByDate:    "+query_by_date_command);
        SQLiteDatabase db= this.getReadableDatabase();
        //cursor the result.
        Cursor cursor= db.rawQuery(query_by_date_command,null);
        if(cursor.moveToFirst()){
            //loop through result and create new task obj and add it to the list.
            do{
                //extract out info from cursor or result.
                int task_id= cursor.getInt(0);
                String item_name=cursor.getString(1);
                //no boolean value from database => stored as integer 1 or 0
                //thus need to determine if it's 1 (true) or 0 (false)
                boolean item_completion = cursor.getInt(2) == 1 ? true :false;
                String item_date=cursor.getString(3);


                //use extracted info to create new Task object.
                Task item= new Task(item_name,item_date,task_id,item_completion);
                //add the new task object to the list.
                task_collection.add(item);
            }while(cursor.moveToNext());

        }else{
            //failure to add anything to list.
        }
        cursor.close();
        db.close();
        return task_collection;
    }


}
