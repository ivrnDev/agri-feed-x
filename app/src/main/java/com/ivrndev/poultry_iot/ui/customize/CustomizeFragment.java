package com.ivrndev.poultry_iot.ui.customize;

import static android.content.Context.MODE_PRIVATE;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ivrndev.poultry_iot.LoginActivity;
import com.ivrndev.poultry_iot.R;
import com.ivrndev.poultry_iot.ScheduleModeActivity;
import com.ivrndev.poultry_iot.SmartFeedingActivity;
import com.ivrndev.poultry_iot.databinding.FragmentCustomizeBinding;

import java.util.Locale;

public class CustomizeFragment extends Fragment {

    private FragmentCustomizeBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.OnSharedPreferenceChangeListener modeChangeListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CustomizeViewModel customizeViewModel =
                new ViewModelProvider(this).get(CustomizeViewModel.class);

        binding = FragmentCustomizeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        setupIcons();
        setupModeWatcher();
        fetchSelectedMode();
        setupListener(customizeViewModel);
        return root;
    }

    public void setupListener(CustomizeViewModel customizeViewModel) {
        binding.resetBtn.setOnClickListener(v -> {
            animateScale(v, () -> {
                sharedPreferences.edit()
                        .remove("bird_type")
                        .remove("growth_stage")
                        .putString("mode", "interval_mode")
                        .apply();
                fetchSelectedMode();
                Toast.makeText(getContext(), "Successfully Reset to default settings", Toast.LENGTH_LONG).show();
            });

        });

        binding.logoutBtn.setOnClickListener(v -> {
            animateScale(v, () -> {
                sharedPreferences.edit().remove("username").apply();

                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            });
        });

        binding.smartFeedingBtn.setOnClickListener(v -> {
            String currentMode = sharedPreferences.getString("mode", null);
            if (!"smart_mode".equals(currentMode)) {
                animateScale(v, () -> {
                    if (!sharedPreferences.contains("bird_type") || !sharedPreferences.contains("growth_stage")) {
                        Intent intent = new Intent(getActivity(), SmartFeedingActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

        binding.scheduleModeBtn.setOnClickListener(v -> {
            String currentMode = sharedPreferences.getString("mode", null);
            if (!"schedule_mode".equals(currentMode)) {
                animateScale(v, () -> {
                    sharedPreferences.edit()
                            .putString("mode", "schedule_mode")
                            .remove("bird_type")
                            .remove("growth_stage")
                            .apply();
                    Intent intent = new Intent(getActivity(), ScheduleModeActivity.class);
                    startActivity(intent);
                });


            }
        });

        binding.intervalModeBtn.setOnClickListener(v -> {
            String currentMode = sharedPreferences.getString("mode", null);
            if (!"interval_mode".equals(currentMode)) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getContext(),
                        (view, hourOfDay, minute) -> {
                            int totalSeconds = hourOfDay;
                            String timeInSeconds = String.format(Locale.getDefault(), "%d", totalSeconds);

                            animateScale(v, () -> {
                                customizeViewModel.setMode(timeInSeconds, isSuccess -> {
                                    sharedPreferences = getContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                                    sharedPreferences.edit()
                                            .putString("mode", "interval_mode")
                                            .remove("bird_type")
                                            .remove("growth_stage")
                                            .apply();
                                    String formattedTime = String.format(Locale.getDefault(), "%02d:00", hourOfDay);
                                    Toast.makeText(getContext(), "Interval mode set to " + formattedTime, Toast.LENGTH_SHORT).show();
                                });
                            });
                        },
                        1,
                        0,
                        true
                );
                timePickerDialog.show();
            }
        });
    }

    public void fetchSelectedMode() {
        String mode = sharedPreferences.getString("mode", null);

        binding.smartFeedingBtn.animate().scaleX(1f).scaleY(1f).setDuration(200).start();
        binding.intervalModeBtn.animate().scaleX(1f).scaleY(1f).setDuration(200).start();
        binding.scheduleModeBtn.animate().scaleX(1f).scaleY(1f).setDuration(200).start();

        if (mode == null) {
            return;
        }

        View selectedView = null;

        switch (mode) {
            case "smart_mode":
                selectedView = binding.smartFeedingBtn;
                break;
            case "interval_mode":
                selectedView = binding.intervalModeBtn;
                break;
            case "schedule_mode":
                selectedView = binding.scheduleModeBtn;
                break;
        }

        if (selectedView != null) {
            selectedView.animate()
                    .scaleX(1.1f)
                    .scaleY(1.1f)
                    .setDuration(200)
                    .start();
        }
    }

    private void setupModeWatcher() {
        modeChangeListener = (sharedPrefs, key) -> {
            if ("mode".equals(key)) fetchSelectedMode();
        };
        sharedPreferences.registerOnSharedPreferenceChangeListener(modeChangeListener);
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

    public void setupIcons() {
        Drawable smartModeIcon = getResources().getDrawable(R.drawable.smart_mode, null);
        Drawable intervalModeIcon = getResources().getDrawable(R.drawable.interval_mode, null);
        Drawable scheduleModeIcon = getResources().getDrawable(R.drawable.schedule_mode, null);

        smartModeIcon.setBounds(0, 0, 280, 280);
        intervalModeIcon.setBounds(0, 0, 280, 280);
        scheduleModeIcon.setBounds(0, 0, 280, 280);
        binding.smartFeedingBtn.setCompoundDrawables(null, null, smartModeIcon, null);
        binding.intervalModeBtn.setCompoundDrawables(null, null, intervalModeIcon, null);
        binding.scheduleModeBtn.setCompoundDrawables(null, null, scheduleModeIcon, null);
    }

    @Override
    public void onDestroyView() {
        if (sharedPreferences != null && modeChangeListener != null) {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(modeChangeListener);
        }
        binding = null;
        super.onDestroyView();
    }
}