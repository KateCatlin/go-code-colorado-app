package com.example.katecatlin.diversityapp.networking;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Service {

    @GET("{path}")
    Call<String> getStats(@Path("path") String path);

}
