package com.ivrndev.poultry_iot.ui.customize.schedule_mode;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.ivrndev.poultry_iot.helper.RetrofitClient;
import com.ivrndev.poultry_iot.service.BlynkApiService;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleModeViewModel extends ViewModel {
    private BlynkApiService blynkApiService = RetrofitClient.getRetrofitInstance().create(BlynkApiService.class);
    private final String token = "zdFJCdIFTD7SbSffjqHU1uDhlFBTRCQv";

    public void setupSchedule(String schedules, Consumer<Boolean> callback) {
        Log.d("Selected Schedules", "setupSchedule: " + schedules);
        blynkApiService.updatePinValue(token, "V5", schedules).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d("Schedule Mode", "Mode updated to schedule mode successfully");
                    callback.accept(true);
                } else {
                    Log.e("Schedule Mode", "Failed to update the mode: " + response);
                    callback.accept(false);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Handle failure
            }
        });
    }
}
