package com.jisa.stepintwoit.api;

import com.jisa.stepintwoit.models.Product;
import com.jisa.stepintwoit.models.ProductResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jisajose on 2018-01-23.
 */

public interface APIInterface {
    @GET("/JisaJose/Stepintwoit/productData")
    Call<ProductResponse> getProducts();

}
