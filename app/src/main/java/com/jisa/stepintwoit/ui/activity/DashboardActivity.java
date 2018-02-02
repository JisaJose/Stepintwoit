package com.jisa.stepintwoit.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jisa.stepintwoit.R;
import com.jisa.stepintwoit.api.APIInterface;
import com.jisa.stepintwoit.api.HttpHandler;
import com.jisa.stepintwoit.api.RetrofitApi;
import com.jisa.stepintwoit.database.SQLiteHelper;
import com.jisa.stepintwoit.models.Product;
import com.jisa.stepintwoit.models.ProductResponse;
import com.jisa.stepintwoit.ui.adapters.UserAdapter;
import com.jisa.stepintwoit.utils.SharedpreferenceUtils;
import com.jisa.stepintwoit.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {
    @BindView(R.id.edt_disp_email)
    TextView edtDisplayEmail;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Product product;
    SharedpreferenceUtils sharedpreferenceUtils;
    ArrayList<Product> globalProductArrayList;
    SQLiteHelper sqLiteHelper;
    private UserAdapter mUserAdapter;
    APIInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sqLiteHelper = new SQLiteHelper(this);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        ButterKnife.bind(this);
//        creating arraylist
        globalProductArrayList = new ArrayList<>();
        mUserAdapter = new UserAdapter(globalProductArrayList, this);
//        pasing adapter calss to recycleView
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mUserAdapter);
        mUserAdapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(Product item) {
                Intent intent = new Intent(DashboardActivity.this, TabActivity.class);
                intent.putExtra(Utils.KEY_PRODUCT, item);
                startActivity(intent);
            }
        });
        //retriving from shared preference and displaying in edit text
        sharedpreferenceUtils = new SharedpreferenceUtils(this);
        String emailId = sharedpreferenceUtils.getValue(Utils.KEY_EMAILID);
        edtDisplayEmail.setText(emailId);
        apiInterface = RetrofitApi.getClient().create(APIInterface.class);
        getProducts();
        // we are using Asyncclass
        //        DataAsync dataAsync = new DataAsync(this, Utils.URL_PHONEDETALS);
        //        dataAsync.execute();

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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button.
        int id = item.getItemId();

        if (id == R.id.menu_profile) {

            Intent intent = new Intent(this,ProfileActivity.class);
            this.startActivity(intent);
            return true;
        }

        if (id == R.id.menu_product_images) {
            Intent intent = new Intent(this,ProductImageActivity.class);
            this.startActivity(intent);
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
    //Using retrofit to make api call and saving in sqlite
    private void getProducts() {
        final ProgressDialog progressdialog = new ProgressDialog(DashboardActivity.this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.show();
        boolean isMakeApi = sqLiteHelper.isMakeApiCall();
        if (isMakeApi) {
            Call<ProductResponse> call = apiInterface.getProducts();
            call.enqueue(new Callback<ProductResponse>() {
                @Override
                public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                    progressdialog.dismiss();
                    ProductResponse productResponse = response.body();
                    globalProductArrayList.addAll(productResponse.products);
                    sqLiteHelper.insertUserDetails(globalProductArrayList);
                    sqLiteHelper.close();
                    mUserAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ProductResponse> call, Throwable t) {
                    t.printStackTrace();
                    progressdialog.dismiss();
                }
            });
        } else {
//            retrieving from sqlite
            globalProductArrayList.addAll(sqLiteHelper.getUsers());
            progressdialog.dismiss();

        }
    }

    //async calss but not using here instead we used retrofit
    private class DataAsync extends AsyncTask<Void, Integer, ArrayList<Product>> {
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
        protected ArrayList<Product> doInBackground(Void... arg0) {
            boolean isMakeApi = sqLiteHelper.isMakeApiCall();
            if (isMakeApi == true) {
                HttpHandler shandler = new HttpHandler(mUrl, null);
                String jsonServerResponse = shandler.makeServiceCall();
                if (jsonServerResponse != null) {
                    try {
                        ArrayList<Product> localProductArrayList = new ArrayList<>();
                        JSONObject jsonObj = new JSONObject(jsonServerResponse);
                        JSONArray userJsonArray = jsonObj.getJSONArray("products");
                        for (int i = 0; i < userJsonArray.length(); i++) {
                            jsonObj = userJsonArray.getJSONObject(i);
                            Product product = new Product();
                            product.setName(jsonObj.getString("name"));
                            product.setDescription(jsonObj.getString("description"));
                            product.setImage(jsonObj.getString("image"));
                            product.setPhone(jsonObj.getString("phone"));
                            localProductArrayList.add(product);
                        }
                        sqLiteHelper.insertUserDetails(localProductArrayList);
                        sqLiteHelper.close();
                        return localProductArrayList;
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
        protected void onPostExecute(ArrayList<Product> result) {
            if (result != null) {
                globalProductArrayList.addAll(result);
                mUserAdapter.notifyDataSetChanged();
            } else {
                Toast toast = Toast.makeText(context, "There is an error ", Toast.LENGTH_LONG);
                toast.show();
            }
        }


    }
}

