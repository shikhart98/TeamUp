package com.example.shikh.teamup.database.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.shikh.teamup.models.teammates;

import java.sql.Array;
import java.sql.SQLData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static com.example.shikh.teamup.database.Consts.*;

/**
 * Created by shikh on 11-01-2018.
 */

public class UserTable {

    public static final String TABLE_NAME = "UserTest";
    public static final String TAG = "usertable";

    private UserTable() {}

    public interface Columns {
        String TEAMNAME = "teamname";
        String NAME = "name";
        String USERID = "userID";
        String PHONE = "phone";
    }

    public static String CMD_CREATE_TABLE =
            CMD_CREATE_TABLE_INE + TABLE_NAME
                    + LBR
                    + Columns.USERID + TYPE_INT +TYPE_PK_AI + COMMA
                    + Columns.TEAMNAME + TYPE_TEXT + COMMA
                    + Columns.NAME + TYPE_TEXT + COMMA
                    + Columns.PHONE + TYPE_INT
                    + RBR + SEMI;

    public static long insertUser(teammates team, SQLiteDatabase db) {
        ContentValues newMem = new ContentValues();
        newMem.put(Columns.TEAMNAME,team.getTeamname());
        newMem.put(Columns.NAME, team.getName());
        newMem.put(Columns.PHONE, team.getPhone());
        return db.insert(TABLE_NAME, null, newMem);
    }
    public static ArrayList<teammates> getTeam(SQLiteDatabase db){
        Cursor c = db.query(
                TABLE_NAME,
                new String[]{Columns.TEAMNAME,Columns.USERID,Columns.NAME,Columns.PHONE},
                null,
                null,
                null,
                null,
                null
        );
        ArrayList<teammates> team = new ArrayList<>();
        c.moveToFirst();
        int teamIndex = c.getColumnIndex(Columns.TEAMNAME);
        int nameIndex = c.getColumnIndex(Columns.NAME);
        int idIndex = c.getColumnIndex(Columns.USERID);
        int phoneIndex = c.getColumnIndex(Columns.PHONE);

        while(!c.isAfterLast()){
            team.add(new teammates(
                    c.getString(teamIndex),
                    c.getString(nameIndex),
                    c.getInt(idIndex),
                    c.getInt(phoneIndex)
            ));
            c.moveToNext();
        }
        Log.d(TAG, "Arraylist of team members sent :)");
        return team;
    }

    public static ArrayList<String> getteamnames(SQLiteDatabase db){
        Cursor c = db.query(TABLE_NAME, new String[]{Columns.TEAMNAME},null,null,null,null,null);
        HashMap<String,Boolean> teamnames = new HashMap<>();
        c.moveToFirst();
        int teamIndex = c.getColumnIndex(Columns.TEAMNAME);
        while(!c.isAfterLast()){
            teamnames.put(c.getString(teamIndex),false);
            c.moveToNext();
        }
        Log.d(TAG, "we get this arraylist >>> "+ teamnames.keySet());
        Set<String> keySet = teamnames.keySet();
        ArrayList<String> arr = new ArrayList<>(keySet);
        return arr;
    }

    public static void delete(SQLiteDatabase db,String teamname){
        db.delete(TABLE_NAME,Columns.TEAMNAME+" = ? ",new String[]{teamname});
    }
}
