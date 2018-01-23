package com.jisa.stepintwoit.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jisa.stepintwoit.R;
import com.jisa.stepintwoit.api.HttpHandler;
import com.jisa.stepintwoit.database.SQLiteHelper;
import com.jisa.stepintwoit.models.User;
import com.jisa.stepintwoit.ui.adapters.UserAdapter;
import com.jisa.stepintwoit.utils.SharedpreferenceUtils;
import com.jisa.stepintwoit.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity {
    @BindView(R.id.edt_disp_email)
    EditText edtDisplayEmail;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    SharedpreferenceUtils sharedpreferenceUtils;
    ArrayList<User> globalUserArrayList;
    String url = Utils.URL_PHONEDETALS;
    SQLiteHelper sqLiteHelper = new SQLiteHelper(this, Utils.DATABASE_NAME, null, Utils.DATABASE_VERSION);
    SQLiteDatabase sqLiteDatabase;
    User user;
    private RecyclerView recyclerView;
    private UserAdapter mUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
//        edtDisplayEmail = (EditText) findViewById(R.id.edt_disp_email);
        ButterKnife.bind(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        globalUserArrayList = new ArrayList<>();
        mUserAdapter = new UserAdapter(globalUserArrayList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mUserAdapter);

        sharedpreferenceUtils = new SharedpreferenceUtils(this);
        String emailId = sharedpreferenceUtils.getValue(Utils.KEY_EMAILID);
        edtDisplayEmail.setText(emailId);
//        sqLiteHelper.deleteAll();


        DataAsync dataAsync = new DataAsync(this, Utils.URL_PHONEDETALS);
        dataAsync.execute();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteHelper.deleteAll();
                edtDisplayEmail.clearComposingText();
                sharedpreferenceUtils.store(Utils.KEY_EMAILID, null);
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private class DataAsync extends AsyncTask<Void, Integer, ArrayList<User>> {
        private Context context;
        String mUrl;

        public DataAsync(Context context, String url) {
            this.context = context;
            mUrl = url;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<User> doInBackground(Void... arg0) {
            boolean isMakeApi = sqLiteHelper.isMakeApiCall();
            if (isMakeApi == true) {

                HttpHandler shandler = new HttpHandler(mUrl, null);
                String jsonServerResponse = shandler.makeServiceCall();
                if (jsonServerResponse != null) {
                    try {
                        ArrayList<User> localUserArrayList = new ArrayList<>();
                        JSONObject jsonObj = new JSONObject(jsonServerResponse);
                        JSONArray userJsonArray = jsonObj.getJSONArray("products");
                        for (int i = 0; i < userJsonArray.length(); i++) {
                            jsonObj = userJsonArray.getJSONObject(i);
                            User user = new User();
                            user.setName(jsonObj.getString("name"));
                            user.setDescription(jsonObj.getString("description"));
                            user.setImage(jsonObj.getString("image"));
                            user.setPhone(jsonObj.getString("phone"));
                            localUserArrayList.add(user);

                        }
                        sqLiteHelper.insertUserDetails(localUserArrayList);
                        sqLiteHelper.close();
                        return localUserArrayList;
                    } catch (final JSONException e) {

                    }
                }
            } else {


                return sqLiteHelper.getUsers();


            }


            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<User> result) {
            if (result != null) {
                globalUserArrayList.addAll(result);
                mUserAdapter.notifyDataSetChanged();
            } else {
                Toast toast = Toast.makeText(context, "There is an error ", Toast.LENGTH_LONG);
                toast.show();
            }
        }


    }
}

