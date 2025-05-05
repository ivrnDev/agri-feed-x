package com.ivrndev.poultry_iot.ui.home;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ivrndev.poultry_iot.helper.RetrofitClient;
import com.ivrndev.poultry_iot.service.BlynkApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<String> powerValue = new MutableLiveData<>();
    private final MutableLiveData<String> refillValue = new MutableLiveData<>();

    private final MutableLiveData<Double> storageValue = new MutableLiveData<>();
    private final BlynkApiService blynkApiService = RetrofitClient.getRetrofitInstance().create(BlynkApiService.class);
    private final String token = "-f79MI8QYI1PWyLqtSvh6NWJ5giBVA_N";

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable updateStorageRunnable;

    public LiveData<String> getPowerValue() {
        return powerValue;
    }

    public LiveData<Double> getStorageValue() {
        return storageValue;
    }

    public void fetchCurrentPowerState() {
        blynkApiService.getPinValue(token, "V0").enqueue(new Callback<Integer>() {
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

    public void fetchRefillState() {
        blynkApiService.getPinValue(token, "V4").enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("response", "onResponse: " + response.body());
                    refillValue.setValue(response.body().toString());
                } else {
                    Log.e("Refill Value", "Failed to fetch refill state: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("Refill Value", "Error fetching refill state", t);
            }
        });
    }

    public void togglePower() {
        String currentValue = powerValue.getValue();
        if (currentValue == null) return;
        String newValue = currentValue.equals("0") ? "1" : "0";

        blynkApiService.updatePinValue(token, "V0", newValue).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d("Toggle Power", "Power state updated successfully");
                    powerValue.setValue(newValue);
                } else {
                    Log.e("Toggle Power", "Failed to update power state: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Toggle Power", "Error updating power state", t);
            }
        });
    }

    public void refill() {
        String currentValue = refillValue.getValue();
        if (currentValue == null) return;
        String newValue = currentValue.equals("1") ? "0" : "1";

        blynkApiService.updatePinValue(token, "V4", newValue).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    refillValue.setValue(newValue);
                } else {
                    Log.e("Refill", "Failed to update refill state: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Refill", "Error updating refill state", t);
            }
        });
    }

    public void fetchCurrentStorage() {
        blynkApiService.getPinValueDouble(token, "V6").enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Double storage = response.body();
                    if (storage != null) {
                        storageValue.setValue(storage);
                    } else {
                        Log.e("Storage", "Storage value is null");
                    }
                } else {
                    Log.e("Storage", "Failed to fetch storage value: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                Log.e("Refill", "Error updating refill state", t);
            }
        });
    }

    public void startPeriodicStorageUpdates() {
        updateStorageRunnable = new Runnable() {
            @Override
            public void run() {
                fetchCurrentStorage();
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(updateStorageRunnable);
    }

    public void stopPeriodicStorageUpdates() {
        if (updateStorageRunnable != null) {
            handler.removeCallbacks(updateStorageRunnable);
        }
    }


}
