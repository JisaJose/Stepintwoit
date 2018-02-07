package com.jisa.stepintwoit.ui.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jisa.stepintwoit.R;
import com.jisa.stepintwoit.models.Product;
import com.jisa.stepintwoit.ui.activity.PhoneDetailsActivity;

import java.util.ArrayList;

/**
 * Created by jisajose on 2018-02-02.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<Product> mproductArrayList;

        public ViewPagerAdapter(Context mContext, ArrayList<Product> productArrayList) {
        this.mContext = mContext;
       mproductArrayList=productArrayList;
    }

    @Override
    public int getCount() {
        return mproductArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);
        Glide.with(mContext)
                .load(mproductArrayList.get(position).getImage())
                .fitCenter() // scale to fit entire image within ImageView
                .into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}

