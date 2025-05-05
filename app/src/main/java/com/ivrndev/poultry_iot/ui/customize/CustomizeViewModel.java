package com.ivrndev.poultry_iot.ui.customize;


import android.util.Log;


import androidx.lifecycle.ViewModel;

import com.ivrndev.poultry_iot.helper.RetrofitClient;
import com.ivrndev.poultry_iot.service.BlynkApiService;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomizeViewModel extends ViewModel {
    private BlynkApiService blynkApiService = RetrofitClient.getRetrofitInstance().create(BlynkApiService.class);
    private final String token = "zdFJCdIFTD7SbSffjqHU1uDhlFBTRCQv";

    public CustomizeViewModel() {
    }

    public void setMode(String time, Consumer<Boolean> callback) {
        Log.d("Interval Mode", "Updating power state to interval mode: " + time);
        blynkApiService.updatePinValue(token, "V1", time).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {

                    Log.d("Interval Mode", "Mode updated to interval mode successfully");
                    callback.accept(true);
                } else {
                    Log.e("Interval Mode", "Failed to update power state: " + response);
                    callback.accept(false);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Interval Mode", "Error updating power state", t);
                callback.accept(false);

            }
        });
    }


}