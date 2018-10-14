package com.example.shikh.teamup.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shikh.teamup.R;
import com.example.shikh.teamup.models.teammates;

import java.util.ArrayList;

/**
 * Created by shikh on 11-01-2018.
 */

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.detailsViewHolder> {

    ArrayList<teammates> team;

    public DetailsAdapter(ArrayList<teammates> team) {
        this.team = team;
    }

    public void setTeam(ArrayList<teammates> team) {
        this.team = team;
        notifyDataSetChanged();
    }

    @Override
    public detailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        return new detailsViewHolder(li.inflate(R.layout.list_users, parent, false));
    }

    @Override
    public void onBindViewHolder(detailsViewHolder holder, int position) {
        holder.BindView(team.get(position));
    }

    @Override
    public int getItemCount() {
        return team.size();
    }

    static class detailsViewHolder extends RecyclerView.ViewHolder {
        TextView list_user_tv_user;

        public detailsViewHolder(View itemView) {
            super(itemView);
            list_user_tv_user = itemView.findViewById(R.id.list_user_tv_user);
        }

        public void BindView(teammates Teammates) {
            list_user_tv_user.setText(Teammates.getName());
        }
    }
}
