package com.codegama.Taskscheduler.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codegama.Taskscheduler.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class cfreminder extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cfreminder);
        //using recyclerview for displaying contest list
        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        //using retrofit for api calling
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://kontests.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceholder json = retrofit.create(JsonPlaceholder.class);
        Call<List<Cfcontest>> call = json.getcontest();
        call.enqueue(new Callback<List<Cfcontest>>() {
            @Override
            public void onResponse(Call<List<Cfcontest>> call, Response<List<Cfcontest>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(cfreminder.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                //getting the contest list and proccessing them in adapter
                List<Cfcontest> contestList = response.body();
                ConestAdapter conAdapter = new ConestAdapter(cfreminder.this , contestList);
                recyclerView.setAdapter(conAdapter);

            }

            @Override
            public void onFailure(Call<List<Cfcontest>> call, Throwable t) {
                Toast.makeText(cfreminder.this, t.getMessage() , Toast.LENGTH_SHORT).show();

            }
        });

    }
}
