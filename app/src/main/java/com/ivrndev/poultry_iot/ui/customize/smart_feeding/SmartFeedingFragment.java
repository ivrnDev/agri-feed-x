package com.ivrndev.poultry_iot.ui.customize.smart_feeding;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ivrndev.poultry_iot.databinding.CustomizeSmartFeedingBinding;

public class SmartFeedingFragment extends Fragment {
    private CustomizeSmartFeedingBinding binding;
    private SharedPreferences sharedPreferences;
    private SmartFeedingViewModel smartFeedingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        smartFeedingViewModel =
                new ViewModelProvider(this).get(SmartFeedingViewModel.class);

        binding = CustomizeSmartFeedingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sharedPreferences = getContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);

        setupListener();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setupListener() {
        binding.henButton.setOnClickListener(v -> {
            animateScale(v, () -> {
                sharedPreferences.edit().putString("bird_type", "hen").apply();
                setPage(1);
            });
        });
        binding.quailButton.setOnClickListener(v -> {
            animateScale(v, () -> {
                sharedPreferences.edit().putString("bird_type", "quail").apply();
                setPage(2);
            });
        });

        setupGrowthStage(binding.henChickBtn, "hen_chick");
        setupGrowthStage(binding.henGrowerBtn, "hen_grower");
        setupGrowthStage(binding.henPrelayingBtn, "hen_prelaying");
        setupGrowthStage(binding.henAdultBtn, "hen_adult");
        setupGrowthStage(binding.henMoltBtn, "hen_molt");

        setupGrowthStage(binding.quailChickBtn, "quail_chick");
        setupGrowthStage(binding.quailGrowerBtn, "quail_grower");
        setupGrowthStage(binding.quailPrelayingBtn, "quail_prelaying");
        setupGrowthStage(binding.quailAdultBtn, "quail_adult");
        setupGrowthStage(binding.quailMoltBtn, "quail_molt");
    }

    private void setupGrowthStage(View view, String stage) {
        view.setOnClickListener(v -> {
            animateScale(v, () -> {
                int time = selectRecommendedSettings(stage);
                if (time != -1) {
                    smartFeedingViewModel.setMode(String.valueOf(time), success -> {
                        sharedPreferences.edit()
                                .putString("mode", "smart_mode")
                                .putString("growth_stage", stage)
                                .apply();
                        Toast.makeText(getContext(), "Smart mode has been activated", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    });
                }
            });
        });
    }

    public int selectRecommendedSettings(String stage) {
        switch (stage) {
            case "hen_chick":
                return 3;
            case "hen_grower":
                return 4;
            case "hen_prelaying":
                return 5;
            case "hen_adult":
                return 6;
            case "hen_molt":
                return 8;
            case "quail_chick":
                return 2;
            case "quail_grower":
                return 3;
            case "quail_prelaying":
                return 4;
            case "quail_adult":
                return 6;
            case "quail_molt":
                return 8;
        }
        return -1;
    }

    public void setPage(int page) {
        binding.viewFlipper.setDisplayedChild(page);
        binding.viewFlipper.invalidate();
    }

    public void animateScale(View view, Runnable onAnimationEnd) {
        view.animate()
                .scaleX(0.95f)
                .scaleY(0.95f)
                .setDuration(100)
                .withEndAction(() -> {
                    view.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(100)
                            .withEndAction(onAnimationEnd)
                            .start();
                })
                .start();
    }
}
