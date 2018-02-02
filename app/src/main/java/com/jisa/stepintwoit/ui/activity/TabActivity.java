package com.jisa.stepintwoit.ui.activity;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jisa.stepintwoit.R;
import com.jisa.stepintwoit.fragments.DescriptionFragment;
import com.jisa.stepintwoit.fragments.ImageFragment;
import com.jisa.stepintwoit.fragments.NameFragment;
import com.jisa.stepintwoit.models.Product;
import com.jisa.stepintwoit.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabActivity extends AppCompatActivity {
    @BindView(R.id.vpPager)
    public ViewPager viewPager;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        ButterKnife.bind(this);
        product = getIntent().getExtras().getParcelable(Utils.KEY_PRODUCT);
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);
    }

    private class TabAdapter extends FragmentPagerAdapter {

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return NameFragment.newInstance(product.getName());
                case 1:
                    return DescriptionFragment.newInstance(product.getDescription());
                case 2:
                    return ImageFragment.newInstance(product.getImage());
                default:
                    return NameFragment.newInstance(product.getName());
            }

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Name";
                case 1:
                    return "Description";
                case 2:
                    return "Image";

            }
            return super.getPageTitle(position);
        }
    }
}
