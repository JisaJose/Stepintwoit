package com.jisa.stepintwoit.ui.adapters;

/**
 * Created by jisajose on 2018-01-19.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jisa.stepintwoit.R;
import com.jisa.stepintwoit.models.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {


    private List<User> userList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, userId;

        public MyViewHolder(View view) {
            super(view);
            userId = (TextView) view.findViewById(R.id.user_id);
            title = (TextView) view.findViewById(R.id.user_title);


        }
    }


    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_user, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        User user = userList.get(position);
        holder.userId.setText("User Id: "+user.getUserId());
        holder.title.setText("Title: "+user.getTitle());

    }

    @Override
    public int getItemCount() {

        return userList!=null ? userList.size():0;
    }
}
