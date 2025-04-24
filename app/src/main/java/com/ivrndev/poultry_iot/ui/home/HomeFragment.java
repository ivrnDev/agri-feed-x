package com.ivrndev.poultry_iot.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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

        homeViewModel.getPowerValue().observe(getViewLifecycleOwner(), value -> {
            int color = value.equals("1") ? getResources().getColor(R.color.green) : getResources().getColor(R.color.red);
            binding.powerBtn.setBackgroundColor(color);

//            binding.powerBtn.setImageResource(
//                    value.equals("1") ? R.drawable.power_state : R.drawable.power_state
//            );
        });

        binding.powerBtn.setOnClickListener(v -> homeViewModel.togglePower());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}