package com.example.shikh.teamup.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shikh.teamup.GActivity;
import com.example.shikh.teamup.R;
import com.example.shikh.teamup.database.DatabaseHelper;
import com.example.shikh.teamup.database.table.UserTable;

import java.util.ArrayList;

/**
 * Created by shikh on 11-01-2018.
 */

public class HomeScreenAdapter extends RecyclerView.Adapter<HomeScreenAdapter.HomeScreenViewHolder> {
    public static final String TAG = "HomeScreenAdapter";
    static ArrayList<String> tn;
    Context context;

    public HomeScreenAdapter(ArrayList<String> tn, Context context) {
        this.tn = tn;
        this.context = context;
    }

    public void setTn(ArrayList<String> tn) {
        this.tn = tn;
        notifyDataSetChanged();
    }

    @Override
    public HomeScreenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        return new HomeScreenViewHolder((li.inflate(R.layout.homescreenitem, parent, false)), context);
    }

    @Override
    public void onBindViewHolder(HomeScreenViewHolder holder, int position) {
        holder.BindView(tn.get(position));
    }

    @Override
    public int getItemCount() {
        return tn.size();
    }

    class HomeScreenViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView item_tv_home;
        Button btn_delete_homescreen;
        RecyclerView rv_homescreen;

        public HomeScreenViewHolder(View itemView, final Context context) {
            super(itemView);
            item_tv_home = itemView.findViewById(R.id.item_tv_home);
            btn_delete_homescreen = itemView.findViewById(R.id.btn_delete_homescreen);
            btn_delete_homescreen.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String team = tn.get(position);
                    Intent i = new Intent(context, GActivity.class);
                    i.putExtra("teamname", tn.get(getAdapterPosition()));
                    context.startActivity(i);
                }
            });
        }

        public void BindView(String str) {
            item_tv_home.setText(str);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_delete_homescreen:
                    Log.d(TAG, "onClick: ");
                    DatabaseHelper myhelper = new DatabaseHelper(itemView.getContext());
                    SQLiteDatabase write = myhelper.getReadableDatabase();
                    Toast.makeText(v.getContext(), "Group: " + tn.get(getAdapterPosition()) + " Deleted. ", Toast.LENGTH_SHORT).show();
                    UserTable.delete(write, tn.get(getAdapterPosition()));
                    tn.remove(getAdapterPosition());
                    tn.trimToSize();
                    notifyDataSetChanged();
                    break;
            }
        }
    }
}
