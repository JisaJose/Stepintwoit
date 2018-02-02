package com.jisa.stepintwoit.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jisa.stepintwoit.R;
import com.jisa.stepintwoit.ui.activity.PhoneDetailsActivity;
import com.jisa.stepintwoit.utils.Utils;

/**
 * Created by jisajose on 2018-01-31.
 */

public class ImageFragment extends Fragment {

    private  String imageUrl;
    public ImageFragment(){

    }
    public  static ImageFragment newInstance(String imageUrl)
    {
        ImageFragment imageFragment=new ImageFragment();
        Bundle bundle= new Bundle();
        bundle.putString(Utils.KEY_IMAGE_URL,imageUrl);
        imageFragment.setArguments(bundle);
        return imageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageUrl=getArguments().getString(Utils.KEY_IMAGE_URL);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
       ImageView imageView = view.findViewById(R.id.img_product);
        Glide.with(ImageFragment.this)
                .load(imageUrl)
                .fitCenter() // scale to fit entire image within ImageView
                .into(imageView);
        return view;
    }
}

