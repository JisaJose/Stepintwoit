package com.jisa.stepintwoit.ui.adapters;

/**
 * Created by jisajose on 2018-01-19.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jisa.stepintwoit.R;
import com.jisa.stepintwoit.models.User;

import java.util.List;

import static java.lang.System.load;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    Context mcontext;


    private List<User> userList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName, txtDescription, txtPhone;
        ImageView img_PhoneImage;

        public MyViewHolder(View view) {
            super(view);
            img_PhoneImage = (ImageView) view.findViewById(R.id.image_phone);
            txtName = (TextView) view.findViewById(R.id.txt_name);
            txtDescription = (TextView) view.findViewById(R.id.txt_description);
            txtPhone = (TextView) view.findViewById(R.id.txt_phone_Number);
        }
    }


    public UserAdapter(List<User> userList, Context context) {
        this.userList = userList;
        mcontext = context;
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
        holder.txtName.setText("Name: " + user.getName());
        holder.txtDescription.setText("Description: " + user.getDescription());
        holder.txtPhone.setText("Name: " + user.getPhone());

        Glide.with(mcontext)
                .load(user.getImage())
                .override(100, 200)
                .fitCenter() // scale to fit entire image within ImageView
                .into(holder.img_PhoneImage);
    }

    @Override
    public int getItemCount() {

        return userList != null ? userList.size() : 0;
    }
}
