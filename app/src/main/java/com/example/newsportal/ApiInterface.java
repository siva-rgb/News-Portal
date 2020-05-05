package com.example.newsportal;

import com.example.newsportal.Model.HeadLine;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Call<HeadLine> getheadline(
            @Query("country") String country,
            @Query("apiKey") String apikey
    );

    @GET("everything")
    Call<HeadLine> getsearch(
            @Query("q") String query,
            @Query("apiKey") String apikey
    );
}
