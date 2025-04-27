package com.ivrndev.poultry_iot.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ekn.gruzer.gaugelibrary.Range;
import com.ivrndev.poultry_iot.R;
import com.ivrndev.poultry_iot.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        observePowerValue(homeViewModel);
        setupListener(homeViewModel);
//        setupFoodLevel();

        return root;
    }

    private void setupFoodLevel() {
        var foodLevel = binding.foodLevelSensor;
        foodLevel.enableAnimation(true);
        foodLevel.setValue(99);
        foodLevel.setMaxValueTextColor(getResources().getColor(R.color.green));
        foodLevel.setMinValueTextColor(getResources().getColor(R.color.red));

        Range lowRange = new Range();
        lowRange.setFrom(0);
        lowRange.setTo(20);
        lowRange.setColor(getResources().getColor(R.color.red));

        Range midRange = new Range();
        midRange.setFrom(20);
        midRange.setTo(80);
        midRange.setColor(getResources().getColor(R.color.yellow));
        Range highRange = new Range();
        highRange.setFrom(80);
        highRange.setTo(100);
        highRange.setColor(getResources().getColor(R.color.green));
        foodLevel.addRange(lowRange);
        foodLevel.addRange(midRange);
        foodLevel.addRange(highRange);
        foodLevel.setMinValue(0);
        foodLevel.setMaxValue(100);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void observePowerValue(HomeViewModel homeViewModel) {
        homeViewModel.getPowerValue().observe(getViewLifecycleOwner(), value -> {
            int color = value.equals("1") ? getResources().getColor(R.color.green) : getResources().getColor(R.color.red);
            binding.powerBtn.setBackgroundColor(color);
        });
    }

    private void setupListener(HomeViewModel homeViewModel) {
        binding.powerBtn.setOnClickListener(v -> homeViewModel.togglePower());
        binding.refillBtn.setOnClickListener(v -> homeViewModel.refill());
        binding.powerBtn.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.scale_animation);
            v.startAnimation(animation);
        });
        binding.refillBtn.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.scale_animation);
            v.startAnimation(animation);
        });

    }


}