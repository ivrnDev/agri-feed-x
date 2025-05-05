package com.ivrndev.poultry_iot.ui.customize.smart_feeding;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.ivrndev.poultry_iot.helper.RetrofitClient;
import com.ivrndev.poultry_iot.service.BlynkApiService;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SmartFeedingViewModel extends ViewModel {
    private BlynkApiService blynkApiService = RetrofitClient.getRetrofitInstance().create(BlynkApiService.class);
    private final String token = "-f79MI8QYI1PWyLqtSvh6NWJ5giBVA_N";

    public SmartFeedingViewModel() {
    }

    public void setMode(String time, Consumer<Boolean> callback) {
        blynkApiService.updatePinValue(token, "V1", time).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d("Smart Mode", "Mode updated to smart mode successfully");
                    callback.accept(true);
                } else {
                    Log.e("Smart Mode", "Failed to update the mode: " + response);
                    callback.accept(false);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Smart Mode", "Error updating the mode", t);
                callback.accept(false);

            }
        });
    }
}
