package com.gauri.retrofitimages;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


interface RequestInteface {
    @GET("5yp6y")//to make a request provided by retrofit
    Call<List<CarsModel>> getCarsJson();//

}
