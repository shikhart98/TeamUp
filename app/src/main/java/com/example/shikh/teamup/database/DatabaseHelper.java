package com.example.shikh.teamup.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shikh.teamup.database.table.TaskTable;
import com.example.shikh.teamup.database.table.UserTable;

/**
 * Created by shikh on 11-01-2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "TeamTest.db";
    public static final int DB_VER = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserTable.CMD_CREATE_TABLE);
        db.execSQL(TaskTable.CMD_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
