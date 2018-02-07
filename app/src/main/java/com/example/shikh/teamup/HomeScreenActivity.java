package com.example.shikh.teamup;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.shikh.teamup.Adapters.HomeScreenAdapter;
import com.example.shikh.teamup.database.DatabaseHelper;
import com.example.shikh.teamup.database.table.UserTable;

public class HomeScreenActivity extends AppCompatActivity {
    RecyclerView rv_homescreen;
    HomeScreenAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        rv_homescreen = findViewById(R.id.rv_homescreen);
        DatabaseHelper myhelper = new DatabaseHelper(this);
        SQLiteDatabase read = myhelper.getReadableDatabase();
        adapter = new HomeScreenAdapter(UserTable.getteamnames(read),this);
        rv_homescreen.setLayoutManager(new LinearLayoutManager(this));
        rv_homescreen.setAdapter(adapter);
        adapter.setTn(UserTable.getteamnames(read));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeScreenActivity.this,fillDetails.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lol", "onResume: comes here");
        DatabaseHelper myhelper = new DatabaseHelper(this);
        SQLiteDatabase read = myhelper.getReadableDatabase();
        adapter = new HomeScreenAdapter(UserTable.getteamnames(read),this);
        adapter.setTn(UserTable.getteamnames(read));
        adapter.notifyDataSetChanged();
        rv_homescreen.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
