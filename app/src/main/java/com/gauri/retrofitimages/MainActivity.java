package com.gauri.retrofitimages;

import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<CarsModel> carsModels=new ArrayList<>();
    private CarsAdapter carsAdapter;
    private RecyclerView cars_recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cars_recyclerview=findViewById(R.id.cars_recyclerview);
        cars_recyclerview.setLayoutManager(new LinearLayoutManager(this));

        getCarsResponse();


        //handeling clicks for recycler view

        cars_recyclerview.addOnItemTouchListener(new CarsAdapter.RecyclerTouchListener(this,
                cars_recyclerview, new CarsAdapter.ClickListener() {


            @Override
            public void onClick(View view, int position) {
                Toast.makeText(MainActivity.this, "Current Position"+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();
            }
        }));




    }

    private void getCarsResponse() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.myjson.com/bins/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInteface requestInteface=retrofit.create(RequestInteface.class);
        Call<List<CarsModel>> call=requestInteface.getCarsJson();

        call.enqueue(new Callback<List<CarsModel>>() {
            @Override
            public void onResponse(Call<List<CarsModel>> call, Response<List<CarsModel>> response) {
                carsModels=new ArrayList<>(response.body());
                carsAdapter=new CarsAdapter(MainActivity.this,carsModels);
                cars_recyclerview.setAdapter(carsAdapter);
                Toast.makeText(MainActivity.this,"Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<CarsModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
