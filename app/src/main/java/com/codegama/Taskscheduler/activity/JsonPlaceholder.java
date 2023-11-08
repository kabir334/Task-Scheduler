package com.codegama.Taskscheduler.activity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceholder {
    @GET("api/v1/codeforces")
    Call<List<Cfcontest>> getcontest();
}
