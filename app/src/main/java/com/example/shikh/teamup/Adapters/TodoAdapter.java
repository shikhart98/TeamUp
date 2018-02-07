package com.example.shikh.teamup.Adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.shikh.teamup.R;
import com.example.shikh.teamup.database.DatabaseHelper;
import com.example.shikh.teamup.database.table.TaskTable;
import com.example.shikh.teamup.models.tasks;

import java.util.ArrayList;

/**
 * Created by shikh on 13-01-2018.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    ArrayList<tasks> Tasks;
    Context context;

    public TodoAdapter(ArrayList<tasks> Tasks, Context context) {
        this.Tasks = Tasks;
        this.context = context;
    }

    public void setTasks(ArrayList<tasks> Tasks) {
        this.Tasks = Tasks;
        notifyDataSetChanged();
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View itemView = li.inflate(R.layout.homescreenitem, parent, false);
        return new TodoViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        holder.bindView(Tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return Tasks.size();
    }

    static class TodoViewHolder extends RecyclerView.ViewHolder {

        TextView item_tv_home;
        Boolean b = true;

        public TodoViewHolder(final View itemView, final Context context) {
            super(itemView);
            item_tv_home = itemView.findViewById(R.id.item_tv_home);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (b) {
                        item_tv_home.setBackgroundColor(Color.argb(25, 0, 255, 0));
                        b = false;
                    } else {
                        item_tv_home.setBackgroundColor(Color.argb(25, 255, 0, 0));
                        b = true;
                    }
                }
            });
        }

        public void bindView(tasks Todo) {
            item_tv_home.setText(Todo.getTaskname());
        }
    }
}