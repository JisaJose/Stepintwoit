package com.jisa.stepintwoit.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.renderscript.Script;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.jisa.stepintwoit.R;
import com.jisa.stepintwoit.database.SQLiteHelper;
import com.jisa.stepintwoit.models.Product;
import com.jisa.stepintwoit.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhoneDetailsActivity extends AppCompatActivity {
    @BindView(R.id.txtPhDetailsName)
    TextView txtPhDetailsName;
    @BindView(R.id.txtPhDetailsDes)
    TextView txtPhDetailsDes;
    @BindView(R.id.txtPhDetailsPhone)
    TextView txtPhDetailsPhone;
    @BindView(R.id.phDetailsImageView)
    ImageView imgPhone;
    @BindView(R.id.imageFavorateButton)
    CheckBox imageFavorateButton;
    Script.LaunchOptions launchDialogue;
    SQLiteHelper sqLiteHelper;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqLiteHelper =  SQLiteHelper.getInstance(this);
        setContentView(R.layout.activity_phone_details);
        ButterKnife.bind(this);
        //parcing  from dashboard activity
        product = getIntent().getExtras().getParcelable(Utils.KEY_PRODUCT);
        txtPhDetailsName.setText(product.getName());
        txtPhDetailsDes.setText(product.getDescription());
        txtPhDetailsPhone.setText(product.getPhone());
        Glide.with(PhoneDetailsActivity.this)
                .load(product.getImage())
                .override(100, 200)
                .fitCenter() // scale to fit entire image within ImageView
                .into(imgPhone);
        boolean isFavorate = product.getImgFavorate().equals("1");

        //open phone dialer screen
        txtPhDetailsPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("tel://" + txtPhDetailsPhone.getText().toString())));
            }
        });

//       onClick event for favorate button
        imageFavorateButton.setChecked(isFavorate);
        imageFavorateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageFavorateButton.isChecked()) {
                    imageFavorateButton.setChecked(true);
                    sqLiteHelper.updateProductFavorate(product.getProductId(), true);
                } else {
                    imageFavorateButton.setChecked(false);
                    sqLiteHelper.updateProductFavorate(product.getProductId(), false);
                }
            }
        });
    }
}
