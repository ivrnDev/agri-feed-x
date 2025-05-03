package com.ivrndev.poultry_iot.ui.customize.smart_feeding;

import android.content.Context;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SmartFeedingViewModel smartFeedingViewModel =
                new ViewModelProvider(this).get(SmartFeedingViewModel.class);

        binding = CustomizeSmartFeedingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
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
            SharedPreferences prefs = getContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("birdType", "hen");
            editor.apply();
            nextPage();
        });
        binding.quailButton.setOnClickListener(v -> {
            SharedPreferences prefs = getContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("birdType", "quail");
            editor.apply();
            nextPage();
        });
    }

    public void nextPage() {
        int currentIndex = binding.viewFlipper.getDisplayedChild();
        binding.viewFlipper.setDisplayedChild(currentIndex + 1);
        binding.viewFlipper.invalidate();  //
    }
}
