package com.ivrndev.poultry_iot.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ekn.gruzer.gaugelibrary.Range;
import com.ivrndev.poultry_iot.R;
import com.ivrndev.poultry_iot.databinding.FragmentHomeBinding;

import java.util.Map;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupFoodLevel();

        observePowerValue(homeViewModel);
        setupListener(homeViewModel);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupFoodLevel() {
        var foodLevel = binding.foodLevelSensor;
        foodLevel.enableAnimation(true);
        foodLevel.setValue(99);
        foodLevel.setMaxValueTextColor(getResources().getColor(R.color.success_color));
        foodLevel.setMinValueTextColor(getResources().getColor(R.color.error_color));

        Range lowRange = new Range();
        lowRange.setFrom(0);
        lowRange.setTo(20);
        lowRange.setColor(getResources().getColor(R.color.error_color));

        Range midRange = new Range();
        midRange.setFrom(20);
        midRange.setTo(80);
        midRange.setColor(getResources().getColor(R.color.yellow));

        Range highRange = new Range();
        highRange.setFrom(80);
        highRange.setTo(100);
        highRange.setColor(getResources().getColor(R.color.success_color));

        foodLevel.addRange(lowRange);
        foodLevel.addRange(midRange);
        foodLevel.addRange(highRange);
        foodLevel.setMinValue(0);
        foodLevel.setMaxValue(100);

        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            foodLevel.setValueColor(Color.WHITE);
            foodLevel.setNeedleColor(Color.WHITE);
        } else {
            foodLevel.setValueColor(Color.BLACK);
            foodLevel.setNeedleColor(Color.BLACK);
        }
    }

    private void observePowerValue(HomeViewModel homeViewModel) {
        homeViewModel.getPowerValue().observe(getViewLifecycleOwner(), value -> {
            Log.d("Power Value", "observePowerValue: " + value);
            Drawable state = value.equals("1") ? ContextCompat.getDrawable(getContext(), R.drawable.power_on_circular) :
                    ContextCompat.getDrawable(getContext(), R.drawable.power_off_circular);
            binding.powerBtn.setBackground(state);
        });
    }

    private void setupListener(HomeViewModel homeViewModel) {
        homeViewModel.fetchCurrentPowerState();
        homeViewModel.fetchRefillState();

        binding.powerBtn.setOnClickListener(v -> {
            homeViewModel.togglePower();
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.scale_animation);
            v.startAnimation(animation);
        });

        binding.refillBtn.setOnClickListener(v -> {
            homeViewModel.refill();
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.scale_animation);
            v.startAnimation(animation);
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
            Map<String, ?> allEntries = sharedPreferences.getAll();

            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                Log.d("SharedPreferences USER PREF", entry.getKey() + ": " + entry.getValue().toString());
            }

        });
    }


}
