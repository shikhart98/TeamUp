package com.example.shikh.teamup;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shikh.teamup.Adapters.TodoAdapter;
import com.example.shikh.teamup.database.DatabaseHelper;
import com.example.shikh.teamup.database.table.TaskTable;
import com.example.shikh.teamup.models.tasks;

import java.util.ArrayList;

public class GActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<tasks> Tasks;
    TodoAdapter adapter;
    RecyclerView rv_taskList;
    String taskname;
    String teamname;
    SQLiteDatabase writeDb;
    SQLiteDatabase read;
    int i=0;
    DatabaseHelper myhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Task Manager");
        Tasks = new ArrayList<>();

        rv_taskList = findViewById(R.id.rv_taskList);
        teamname = getIntent().getStringExtra("teamname");

        adapter = new TodoAdapter(Tasks,this);
        myhelper = new DatabaseHelper(this);
        writeDb = myhelper.getWritableDatabase();
        read = myhelper.getReadableDatabase();

        adapter.setTasks(TaskTable.getTasks(read));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(GActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.enter_task, null);
                final EditText et_enteredtask = (EditText) mView.findViewById(R.id.et_enteredtask);
                Button btn_addtask = (Button) mView.findViewById(R.id.btn_addtask);
                btn_addtask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!et_enteredtask.getText().toString().isEmpty()){
                            tasks Task = new tasks(i,et_enteredtask.getText().toString(),teamname,false);

                            TaskTable.insertTask(Task,writeDb);

                            et_enteredtask.setText("");
                            adapter.setTasks(TaskTable.getTasksbyteamname(read,teamname));
                            rv_taskList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                            rv_taskList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            i++;
                            Toast.makeText(getBaseContext(),"Task "+et_enteredtask.getText().toString()+" added successfully",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getBaseContext(),"Write something in the field",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
        adapter.setTasks(TaskTable.getTasksbyteamname(read,teamname));
        rv_taskList.setLayoutManager(new LinearLayoutManager(this));
        rv_taskList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    protected void onPostResume() {
        myhelper = new DatabaseHelper(this);
        read = myhelper.getReadableDatabase();
        adapter = new TodoAdapter(TaskTable.getTasksbyteamname(read,teamname),this);
        adapter.setTasks(TaskTable.getTasksbyteamname(read,teamname));
        adapter.notifyDataSetChanged();
        super.onPostResume();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.g, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

            String str="";
            int j=1;
            StringBuilder s = new StringBuilder("");

            for(String k:TaskTable.getTasknamebyteamname(read,teamname)){
//                s = j+". "+k+"\n";
                s.append(j+". "+k+"\n");
                j++;
            }

            Log.d("check", "onNavigationItemSelected: "+str);
            Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","bhavyarkochawla@gmail.com;rupavwinchester@gmail.com",null));
            i.putExtra(i.EXTRA_SUBJECT,"Your Task");
            i.putExtra(i.EXTRA_TEXT,"Team :" + teamname +"\n" + "These Tasks are to be done :\n\n " + s.toString());
            startActivity(Intent.createChooser(i,"Choose one!"));

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
