package com.ivrndev.poultry_iot.ui.customize.smart_feeding;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ivrndev.poultry_iot.databinding.CustomizeSmartFeedingBinding;

public class SmartFeedingFragment extends Fragment {
    private CustomizeSmartFeedingBinding binding;
    private SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SmartFeedingViewModel smartFeedingViewModel =
                new ViewModelProvider(this).get(SmartFeedingViewModel.class);

        binding = CustomizeSmartFeedingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sharedPreferences = getContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);

        setupListener(smartFeedingViewModel);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setupListener(SmartFeedingViewModel smartFeedingViewModel) {
        binding.viewFlipper.setInAnimation(requireContext(), android.R.anim.slide_in_left);

        binding.henButton.setOnClickListener(v -> {
            animateScale(v, () -> {
                sharedPreferences.edit().putString("birdType", "hen");
                sharedPreferences.edit().apply();
                setPage(1);
            });
        });
        binding.quailButton.setOnClickListener(v -> {
            animateScale(v, () -> {
                sharedPreferences.edit().putString("birdType", "quail");
                sharedPreferences.edit().apply();
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
                sharedPreferences.edit().putString("growth_stage", stage).apply();
                getActivity().finish();
            });
        });
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
