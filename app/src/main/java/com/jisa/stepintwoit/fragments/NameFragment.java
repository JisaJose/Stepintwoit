package com.jisa.stepintwoit.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jisa.stepintwoit.R;
import com.jisa.stepintwoit.utils.Utils;

/**
 * Created by jisajose on 2018-01-31.
 */

public class NameFragment extends Fragment {
    private  String name;
    public NameFragment(){

    }
    public  static NameFragment newInstance(String name)
    {
        NameFragment nameFragment=new NameFragment();
        Bundle bundle= new Bundle();
        bundle.putString(Utils.KEY_NAME,name);
        nameFragment.setArguments(bundle);
        return nameFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name=getArguments().getString(Utils.KEY_NAME);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.texProductName);
        tvLabel.setText("Name" + " -- " + name);
        return view;
    }
}
