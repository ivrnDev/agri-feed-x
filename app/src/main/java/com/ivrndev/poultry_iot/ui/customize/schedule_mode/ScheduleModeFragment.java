package com.ivrndev.poultry_iot.ui.customize.schedule_mode;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ivrndev.poultry_iot.databinding.CustomizeIntervalModeBinding;
import com.ivrndev.poultry_iot.databinding.CustomizeScheduleModeBinding;
import com.ivrndev.poultry_iot.ui.customize.smart_feeding.SmartFeedingViewModel;

public class ScheduleModeFragment extends Fragment {
    private CustomizeScheduleModeBinding binding;

    private SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SmartFeedingViewModel smartFeedingViewModel =
                new ViewModelProvider(this).get(SmartFeedingViewModel.class);

        binding = CustomizeScheduleModeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sharedPreferences = getContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
