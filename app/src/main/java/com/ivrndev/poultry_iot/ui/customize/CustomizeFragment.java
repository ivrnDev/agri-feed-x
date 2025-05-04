package com.ivrndev.poultry_iot.ui.customize;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ivrndev.poultry_iot.LoginActivity;
import com.ivrndev.poultry_iot.R;
import com.ivrndev.poultry_iot.SmartFeedingActivity;
import com.ivrndev.poultry_iot.databinding.FragmentCustomizeBinding;

public class CustomizeFragment extends Fragment {

    private FragmentCustomizeBinding binding;
    private SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CustomizeViewModel customizeViewModel =
                new ViewModelProvider(this).get(CustomizeViewModel.class);

        binding = FragmentCustomizeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sharedPreferences = getContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);

        setupListener(customizeViewModel);
        return root;
    }

    public void setupListener(CustomizeViewModel customizeViewModel) {
        binding.resetBtn.setOnClickListener(v -> {
            animate(v);
            sharedPreferences.edit().remove("bird_type").apply();
            sharedPreferences.edit().remove("growth_stage").apply();
            Toast.makeText(getContext(), "Successfully Reset to default settings", Toast.LENGTH_LONG).show();
        });
        binding.logoutBtn.setOnClickListener(v -> {
            animate(v);
            sharedPreferences.edit().remove("username").apply();

            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
        });

        binding.smartFeedingBtn.setOnClickListener(v -> {
            animate(v);
            if (!sharedPreferences.contains("bird_type") && !sharedPreferences.contains("growth_stage")) {
                Intent intent = new Intent(getActivity(), SmartFeedingActivity.class);
                startActivity(intent);
            }
        });
    }

    public void animate(View view) {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.scale_animation);
        view.startAnimation(animation);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}