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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shikh.teamup.Adapters.DetailsAdapter;
import com.example.shikh.teamup.Adapters.HomeScreenAdapter;
import com.example.shikh.teamup.database.DatabaseHelper;
import com.example.shikh.teamup.database.table.UserTable;
import com.example.shikh.teamup.models.teammates;

import java.util.ArrayList;

public class fillDetails extends AppCompatActivity {

    public static final String TAG = "FillDetails";

    EditText fd_et_teamname,fd_et_addMem;
    TextView fd_tv_teamname, fd_tv_addMem;
    ArrayList<teammates> team;
    RecyclerView fd_rv;
    DetailsAdapter adapter;
    Button fill_detail_addme;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        team = new ArrayList<>();
        setTitle("Fill Details");
        fill_detail_addme = findViewById(R.id.fill_detail_addme);
        fd_et_addMem = findViewById(R.id.fd_et_addMem);
        fd_et_teamname = findViewById(R.id.fd_et_teamname);
        fd_tv_addMem = findViewById(R.id.fd_tv_addMem);
        fd_tv_teamname = findViewById(R.id.fd_tv_teamname);
        fd_rv = findViewById(R.id.fd_rv);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        adapter = new DetailsAdapter(team);
        DatabaseHelper mydbHelper = new DatabaseHelper(this);
        final SQLiteDatabase writeDB = mydbHelper.getWritableDatabase();
        final SQLiteDatabase readDB = mydbHelper.getReadableDatabase();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(fillDetails.this,"Group Added",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        fill_detail_addme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teammates t = new teammates(fd_et_teamname.getText().toString(),fd_et_addMem.getText().toString(), i, 0);
                UserTable.insertUser(t, writeDB);
                i++;
                fd_et_addMem.setText("");
                team.add(t);
                Log.d(TAG, ">>>" + UserTable.getTeam(readDB) + "<<<");
            }
        });
        fd_rv.setLayoutManager(new LinearLayoutManager(this));
        fd_rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
