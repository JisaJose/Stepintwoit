package com.jisa.stepintwoit.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jisa.stepintwoit.R;
import com.jisa.stepintwoit.api.APIInterface;
import com.jisa.stepintwoit.api.RetrofitApi;
import com.jisa.stepintwoit.database.SQLiteHelper;
import com.jisa.stepintwoit.models.Product;
import com.jisa.stepintwoit.utils.SharedpreferenceUtils;
import com.jisa.stepintwoit.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProfileActivity extends AppCompatActivity {
    private static final int SELECT_PICTURE = 1;
    SharedpreferenceUtils sharedpreferenceUtils;
    ArrayList<Product> globalProductArrayList;
    SQLiteHelper sqLiteHelper;
    @BindView(R.id.txt_profile_email)
    TextView edtDisplayEmail;
    @BindView(R.id.btn_profile_logout)
    Button btnLogout;
    @BindView(R.id.img_profile_image)
    ImageView imgProfile;
    APIInterface apiInterface;
    private static int RESULT_LOAD_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sqLiteHelper = new SQLiteHelper(this);
        ButterKnife.bind(this);
        sharedpreferenceUtils = new SharedpreferenceUtils(this);
        String emailId = sharedpreferenceUtils.getValue(Utils.KEY_EMAILID);
        edtDisplayEmail.setText(emailId);
        apiInterface = RetrofitApi.getClient().create(APIInterface.class);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sqLiteHelper.deleteAll();
                edtDisplayEmail.clearComposingText();
                sharedpreferenceUtils.store(Utils.KEY_EMAILID, null);
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, RESULT_LOAD_IMAGE);

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imgProfile.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(ProfileActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(ProfileActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

}
