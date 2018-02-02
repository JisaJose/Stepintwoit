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

public class DescriptionFragment extends Fragment {
    private  String description;
    public DescriptionFragment (){

    }
    public  static DescriptionFragment newInstance(String description)
    {
        DescriptionFragment descriptionFragment=new DescriptionFragment();
        Bundle bundle= new Bundle();
        bundle.putString(Utils.KEY_DESCRIPTION,description);
        descriptionFragment.setArguments(bundle);
        return descriptionFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        description=getArguments().getString(Utils.KEY_DESCRIPTION);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_description, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.textDesProduct);
        tvLabel.setText("Description" + " -- " + description);
        return view;
    }
}


