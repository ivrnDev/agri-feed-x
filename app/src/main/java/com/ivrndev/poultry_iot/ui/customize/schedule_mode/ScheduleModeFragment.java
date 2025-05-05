package com.ivrndev.poultry_iot.ui.customize.schedule_mode;

import static android.content.Context.MODE_PRIVATE;

import android.app.TimePickerDialog;
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

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.ivrndev.poultry_iot.R;
import com.ivrndev.poultry_iot.databinding.CustomizeScheduleModeBinding;
import com.ivrndev.poultry_iot.ui.customize.smart_feeding.SmartFeedingViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ScheduleModeFragment extends Fragment {
    private CustomizeScheduleModeBinding binding;

    private SharedPreferences sharedPreferences;

    private final List<String> selectedTimes = new ArrayList<>();
    private int scheduleCount = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ScheduleModeViewModel scheduleModeViewModel =
                new ViewModelProvider(this).get(ScheduleModeViewModel.class);

        binding = CustomizeScheduleModeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sharedPreferences = getContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        setupListener(scheduleModeViewModel);

        return root;
    }

    public void setupListener(ScheduleModeViewModel scheduleModeViewModel) {
        binding.addBtn.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.scale_animation);
            binding.addBtn.startAnimation(animation);

            if (scheduleCount == 10) {
                Toast.makeText(getContext(), "Maximum of 10 cycle allowed", Toast.LENGTH_SHORT).show();
                return;
            }

            scheduleCount++;

            View row = LayoutInflater.from(getContext()).inflate(R.layout.item_schedule_row, binding.scheduleContainer, false);
            MaterialTextView cycleTextView = row.findViewById(R.id.cycleTextView);
            MaterialButton selectTimeButton = row.findViewById(R.id.timeButton);

            String suffix;
            if (scheduleCount == 1) suffix = "1st";
            else if (scheduleCount == 2) suffix = "2nd";
            else if (scheduleCount == 3) suffix = "3rd";
            else suffix = scheduleCount + "th";

            cycleTextView.setText(suffix + " Cycle");

            selectedTimes.add("");
            final int index = selectedTimes.size() - 1;

            selectTimeButton.setOnClickListener(btn -> {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getContext(),
                        (view, hourOfDay, minute1) -> {
                            String time = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute1);
                            selectTimeButton.setText(time);
                            selectedTimes.set(index, time);
                        },
                        1, 0, true
                );
                timePickerDialog.show();
            });

//            binding.minusBtn.setOnClickListener(v1 -> {
//                if (index >= 0 && index < selectedTimes.size()) {
//                    binding.scheduleContainer.removeView(row);
//                    selectedTimes.remove(index);
//                    scheduleCount--;
//                }
//                Log.d("CURRENT INDEX", "index: " + index);
//            });

            binding.scheduleContainer.addView(row);
        });
        binding.submitBtn.setOnClickListener(v -> {
            if (selectedTimes.size() == 0 || selectedTimes.get(0).isEmpty()) {
                Toast.makeText(getContext(), "Please select at least one time", Toast.LENGTH_SHORT).show();
                return;
            }

            StringBuilder scheduleData = new StringBuilder();
            for (String time : selectedTimes) {
                if (!time.isEmpty()) {
                    if (scheduleData.length() > 0) scheduleData.append(",");
                    scheduleData.append(time);
                }
            }

            scheduleModeViewModel.setupSchedule(scheduleData.toString(), isSuccess -> {
                if (!isSuccess) {
                    Toast.makeText(getContext(), "Failed to schedule", Toast.LENGTH_SHORT).show();
                    return;
                }

                sharedPreferences.edit()
                        .putString("mode", "schedule_mode")
                        .remove("bird_type")
                        .remove("growth_stage")
                        .apply();
                Toast.makeText(getContext(), "Scheduled Successfully", Toast.LENGTH_SHORT).show();
            });

            getActivity().finish();

        });
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
