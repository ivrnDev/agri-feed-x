package com.ivrndev.poultry_iot.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ivrndev.poultry_iot.RetrofitClient;
import com.ivrndev.poultry_iot.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<String> powerValue = new MutableLiveData<>();
    private final ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private final String token = "-f79MI8QYI1PWyLqtSvh6NWJ5giBVA_N";

    public LiveData<String> getPowerValue() {
        return powerValue;
    }

    // Fetch the current power state
    public void fetchCurrentPowerState() {
        apiService.getPinValue(token, "V0").enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    powerValue.setValue(response.body().toString());
                } else {
                    Log.e("HomeViewModel1", "Failed to fetch power state: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("HomeViewModel1", "Error fetching power state", t);
            }
        });
    }

    public void togglePower() {
        fetchCurrentPowerState();
        powerValue.observeForever(currentValue -> {
            String newValue = currentValue.equals("1") ? "0" : "1";
            apiService.updatePinValue(token, "V0", newValue).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful()) {
                        powerValue.setValue(newValue);
                    } else {
                        Log.e("HomeViewModel2", "Failed to update power state: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Log.e("HomeViewModel2", "Error updating power state", t);
                }
            });
        });
    }
}