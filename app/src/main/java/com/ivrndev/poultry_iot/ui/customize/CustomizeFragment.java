package com.ivrndev.poultry_iot.ui.customize;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ivrndev.poultry_iot.LoginActivity;
import com.ivrndev.poultry_iot.SmartFeedingActivity;
import com.ivrndev.poultry_iot.databinding.FragmentCustomizeBinding;

public class CustomizeFragment extends Fragment {

    private FragmentCustomizeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CustomizeViewModel customizeViewModel =
                new ViewModelProvider(this).get(CustomizeViewModel.class);

        binding = FragmentCustomizeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setupListener(customizeViewModel);
        return root;
    }

    public void setupListener(CustomizeViewModel customizeViewModel) {
        binding.logoutBtn.setOnClickListener(v -> {
            SharedPreferences prefs = getContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove("username");
            editor.apply();

            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
        });

        binding.smartFeedingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SmartFeedingActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}