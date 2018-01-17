package com.jisa.stepintwoit.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jisa.stepintwoit.R;
import com.jisa.stepintwoit.api.HttpHandler;
import com.jisa.stepintwoit.utils.SharedpreferenceUtils;
import com.jisa.stepintwoit.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.http.Url;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.pgb_login)
    ProgressBar pgbLogin;
    @BindView(R.id.txt_progressbar)
    TextView txtProgressbar;
    @BindView(R.id.btn_submit)
    Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_submit)
    public void login(View view) {
String email=edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
//        boolean isValidEmail = ValidationUtils.isValidEmail(edtEmail.getText().toString());
        int lengthPassword = password.length();
//        if (!isValidEmail) {
//            Toast toast = Toast.makeText(this, getResources().getString(R.string.error_email), Toast.LENGTH_LONG);
//            toast.show();
//        }
       if (lengthPassword < 6) {
            Toast toast = Toast.makeText(this, getResources().getString(R.string.error_password), Toast.LENGTH_LONG);
            toast.show();
        } else {
LoginAsynTask loginAsynTask=  new LoginAsynTask(Utils.URL_POST);
loginAsynTask.execute(email,password);


        }
    }

    private class LoginAsynTask extends AsyncTask<String, Integer, String> {
       String mUrl;
        public LoginAsynTask(String url){
            mUrl=url;
        }

        @Override
        protected void onPreExecute() {
            btnSubmit.setEnabled(false);
            pgbLogin.setVisibility(View.VISIBLE);
            txtProgressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            JSONObject jsonObj = new JSONObject();
            try {
                jsonObj.put("email", params[0]);
                jsonObj.put("password",params[1] );
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpHandler shandler = new HttpHandler(mUrl,jsonObj.toString());
            String jsonStr = shandler.makeServiceCall(mUrl);
            JSONObject responceJsonObject= null;
            try {
                responceJsonObject = new JSONObject(jsonStr);
                String tokenValue=responceJsonObject.getString("token");
                Log.e("testtoken","your token is"+tokenValue);
                return tokenValue;
            } catch (JSONException e) {
                e.printStackTrace();
            }


//                for (int i = 0; i < 5; i++) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                    publishProgress(i);
//                }

                return  null;


        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            txtProgressbar.setText("Seconds:" + values[0] + "/5");
        }

        @Override
        protected void onPostExecute(String result) {

            btnSubmit.setEnabled(true);
            pgbLogin.setVisibility(View.GONE);
            txtProgressbar.setVisibility(View.GONE);
            SharedpreferenceUtils sharedpreferenceUtils = new SharedpreferenceUtils(LoginActivity.this);
          sharedpreferenceUtils.store(Utils.KEY_TOKEN,result);
            sharedpreferenceUtils.store(Utils.KEY_EMAILID,edtEmail.getText().toString());
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            intent.putExtra(Utils.EXTRA_EMAILID, edtEmail.getText().toString());
            startActivity(intent);
            finish();
        }
    }
}
