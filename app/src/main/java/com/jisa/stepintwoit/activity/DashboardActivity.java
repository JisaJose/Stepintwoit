package com.jisa.stepintwoit.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jisa.stepintwoit.R;
import com.jisa.stepintwoit.api.HttpHandler;
import com.jisa.stepintwoit.utils.SharedpreferenceUtils;
import com.jisa.stepintwoit.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity {
    @BindView(R.id.edt_disp_email)
    EditText edtDisplayEmail;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    SharedpreferenceUtils sharedpreferenceUtils;
    ArrayList<HashMap<String, String>> loginDetails;
String url=Utils.URL_GET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
//        edtDisplayEmail = (EditText) findViewById(R.id.edt_disp_email);
        ButterKnife.bind(this);

        sharedpreferenceUtils = new SharedpreferenceUtils(this);
        String emailId = sharedpreferenceUtils.getValue(Utils.KEY_EMAILID);
        edtDisplayEmail.setText(emailId);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDetails = new ArrayList<>();
                DisplayLoginDetals displayLoginDetals = new DisplayLoginDetals(Utils.URL_GET);
                DisplayLoginDetals.execute();
                sharedpreferenceUtils.store(Utils.KEY_EMAILID, null);
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private class DisplayLoginDetals extends AsyncTask<Void, Integer, ArrayList<HashMap<String, String>>> {
        String mUrl;

        public DisplayLoginDetals(String url) {
            mUrl = url;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(Void... arg0) {

            HttpHandler shandler = new HttpHandler();
            String jsonStr = shandler.makeServiceCall(mUrl);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray details = jsonObj.getJSONArray("");
                    for (int i = 0; i < details.length(); i++) {
                       jsonObj = details.getJSONObject(i);
                        String userId = jsonObj.getString("userId");
                        String id = jsonObj.getString("id");
                        String title = jsonObj.getString("title");
                        String body = jsonObj.getString("body");
                        HashMap<String, String> hshdetail = new HashMap<>();
                        hshdetail.put("id", id);
                        hshdetail.put("userId", userId);
                        hshdetail.put("title", title);
                        hshdetail.put("body", body);
                        loginDetails.add(hshdetail);
                        return loginDetails;
                    }
                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });

            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> s) {

            super.onPostExecute(s);
        }


    }
}

