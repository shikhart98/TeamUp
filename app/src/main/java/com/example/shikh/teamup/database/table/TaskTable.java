package com.example.shikh.teamup.database.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.shikh.teamup.models.tasks;
import com.example.shikh.teamup.models.teammates;

import java.util.ArrayList;

import static com.example.shikh.teamup.database.Consts.CMD_CREATE_TABLE_INE;
import static com.example.shikh.teamup.database.Consts.COMMA;
import static com.example.shikh.teamup.database.Consts.LBR;
import static com.example.shikh.teamup.database.Consts.TYPE_INT;
import static com.example.shikh.teamup.database.Consts.TYPE_PK_AI;
import static com.example.shikh.teamup.database.Consts.*;

/**
 * Created by shikh on 13-01-2018.
 */

public class TaskTable {

    public static final String TABLE_NAME = "TaskTest";
    public static final String TAG = "tasktable";

    private TaskTable() {
    }

    public interface Columns {
        String ID = "id";
        String TASK_NAME = "taskname";
        String TEAM_NAME = "teamname";
        String DONE = "done";
    }

    public static String CMD_CREATE_TABLE =
            CMD_CREATE_TABLE_INE + TABLE_NAME
                    + LBR
                    + Columns.ID + TYPE_INT + TYPE_PK_AI + COMMA
                    + Columns.TASK_NAME + TYPE_TEXT + COMMA
                    + Columns.TEAM_NAME + TYPE_TEXT + COMMA
                    + Columns.DONE + TYPE_BOOLEAN
                    + RBR + SEMI;

    public static long insertTask(tasks task, SQLiteDatabase db) {
        ContentValues newTask = new ContentValues();
        newTask.put(Columns.DONE, task.getDone());
        newTask.put(Columns.TASK_NAME, task.getTaskname());
        newTask.put(Columns.TEAM_NAME, task.getTeamname());
        return db.insert(TABLE_NAME, null, newTask);
    }

    public static ArrayList<tasks> getTasks(SQLiteDatabase db) {
        Cursor c = db.query(
                TABLE_NAME,
                new String[]{Columns.ID, Columns.TASK_NAME, Columns.TEAM_NAME, Columns.DONE},
                null,
                null,
                null,
                null,
                null
        );
        ArrayList<tasks> Tasks = new ArrayList<>();
        c.moveToFirst();
        int idIndex = c.getColumnIndex(Columns.ID);
        int tasknameIndex = c.getColumnIndex(Columns.TASK_NAME);
        int teamnameIndex = c.getColumnIndex(Columns.TEAM_NAME);
        int doneIndex = c.getColumnIndex(Columns.DONE);

        while (!c.isAfterLast()) {
            Tasks.add(new tasks(
                    c.getInt(idIndex),
                    c.getString(tasknameIndex),
                    c.getString(teamnameIndex),
                    c.getInt(doneIndex) == 1
            ));
            c.moveToNext();
        }
        return Tasks;
    }

    public static ArrayList<tasks> getTasksbyteamname(SQLiteDatabase db, String teamname1) {
        Cursor c = db.query(
                TABLE_NAME,
                new String[]{Columns.ID, Columns.TASK_NAME, Columns.TEAM_NAME, Columns.DONE},
                "teamname = ?",
                new String[]{teamname1},
                null,
                null,
                null
        );
        ArrayList<tasks> Tasks = new ArrayList<>();
        c.moveToFirst();
        int idIndex = c.getColumnIndex(Columns.ID);
        int tasknameIndex = c.getColumnIndex(Columns.TASK_NAME);
        int teamnameIndex = c.getColumnIndex(Columns.TEAM_NAME);
        int doneIndex = c.getColumnIndex(Columns.DONE);

        while (!c.isAfterLast()) {
            Tasks.add(new tasks(
                    c.getInt(idIndex),
                    c.getString(tasknameIndex),
                    c.getString(teamnameIndex),
                    c.getInt(doneIndex) == 1
            ));
            c.moveToNext();
        }
        Log.d(TAG, "getTasksbyteamname: " + Tasks);
        return Tasks;
    }

    public static ArrayList<String> getTasknamebyteamname(SQLiteDatabase db, String teamname1) {
        Cursor c = db.query(
                TABLE_NAME,
                new String[]{Columns.TASK_NAME},
                "teamname = ?",
                new String[]{teamname1},
                null,
                null,
                null
        );
        ArrayList<String> Tasks = new ArrayList<>();
        c.moveToFirst();
        int tasknameIndex = c.getColumnIndex(Columns.TASK_NAME);

        while (!c.isAfterLast()) {
            Tasks.add(c.getString(tasknameIndex));
            c.moveToNext();
        }
        Log.d(TAG, "getTasksbyteamname: " + Tasks);
        return Tasks;
    }
}
