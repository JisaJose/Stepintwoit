package com.jisa.stepintwoit.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jisa.stepintwoit.R;
import com.jisa.stepintwoit.api.HttpHandler;
import com.jisa.stepintwoit.models.User;
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
    String url = Utils.URL_GET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
//        edtDisplayEmail = (EditText) findViewById(R.id.edt_disp_email);
        ButterKnife.bind(this);

        sharedpreferenceUtils = new SharedpreferenceUtils(this);
        String emailId = sharedpreferenceUtils.getValue(Utils.KEY_EMAILID);
        edtDisplayEmail.setText(emailId);

        globalUserArrayList = new ArrayList<>();

        DisplayLoginDetals displayLoginDetals = new DisplayLoginDetals(Utils.URL_GET);
        displayLoginDetals.execute();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedpreferenceUtils.store(Utils.KEY_EMAILID, null);
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private class DisplayLoginDetals extends AsyncTask<Void, Integer, ArrayList<User>> {
        String mUrl;

        public DisplayLoginDetals(String url) {
            mUrl = url;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<User> doInBackground(Void... arg0) {

            HttpHandler shandler = new HttpHandler(mUrl, null);
            String jsonServerResponse = shandler.makeServiceCall();
            if (jsonServerResponse != null) {
                try {
                    ArrayList<User> localUserArrayList = new ArrayList<>();
                    JSONArray userJsonArray = new JSONArray(jsonServerResponse);
                    for (int i = 0; i < userJsonArray.length(); i++) {
                        JSONObject jsonObj = userJsonArray.getJSONObject(i);
                        User user = new User();
                        user.setId(jsonObj.getInt("id"));
                        user.setUserId(jsonObj.getInt("userId"));
                        user.setTitle(jsonObj.getString("title"));
                        user.setBody(jsonObj.getString("body"));
                        localUserArrayList.add(user);
                    }
                    return localUserArrayList;
                } catch (final JSONException e) {

                }
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
            }
            Log.e("SIZE", "the size of this arraylist" + result.size());
        }


    }
}

