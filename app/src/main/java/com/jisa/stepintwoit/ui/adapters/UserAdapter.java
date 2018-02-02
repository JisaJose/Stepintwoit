package com.jisa.stepintwoit.ui.adapters;

/**
 * Created by jisajose on 2018-01-19.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jisa.stepintwoit.R;
import com.jisa.stepintwoit.database.SQLiteHelper;
import com.jisa.stepintwoit.models.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private Context mcontext;
    private SQLiteHelper sqLiteHelper;
    private OnItemClickListener onItemClickListener;
    private List<Product> productList;

    public UserAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        mcontext = context;
        sqLiteHelper = new SQLiteHelper(context);
    }

    public interface OnItemClickListener {
        void onItemClick(Product item);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_user, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Product product = productList.get(position);
        holder.txtName.setText("Name: " + product.getName());
        holder.txtDescription.setText("Description: " + product.getDescription());
        holder.txtPhone.setText("Phone: " + product.getPhone());
        Glide.with(mcontext)
                .load(product.getImage())
                .override(100, 200)
                .fitCenter() // scale to fit entire image within ImageView
                .into(holder.img_PhoneImage);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(product);

            }
        };
        holder.itemView.setOnClickListener(listener);


        final boolean isFavorateChecked = product.getImgFavorate().equals("1");
        holder.chkBoxFavorateButtton.setChecked(isFavorateChecked);


        holder.chkBoxFavorateButtton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                sqLiteHelper.updateProductFavorate(product.getProductId(), isChecked);
                productList.get(position).setImgFavorate(isChecked ? "1" : "0");

            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_description)
        TextView txtDescription;
        @BindView(R.id.txt_phone_Number)
        TextView txtPhone;
        @BindView(R.id.circleView)
        ImageView img_PhoneImage;
        @BindView(R.id.chkbox_adapter_favorate_buttton)
        CheckBox chkBoxFavorateButtton;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public int getItemCount() {

        return productList != null ? productList.size() : 0;
    }
}
