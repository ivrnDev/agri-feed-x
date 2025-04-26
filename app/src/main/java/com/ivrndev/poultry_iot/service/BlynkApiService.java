package com.ivrndev.poultry_iot.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BlynkApiService {
    @GET("external/api/get")
    Call<Integer> getPinValue(
            @Query("token") String token,
            @Query("pin") String pin
    );

    // Update value to virtual pin
    @GET("external/api/update")
    Call<Integer> updatePinValue(
            @Query("token") String token,
            @Query("pin") String pin,
            @Query("value") String value
    );

}
