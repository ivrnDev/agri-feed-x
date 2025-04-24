package com.ivrndev.poultry_iot.ui.customize;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ivrndev.poultry_iot.databinding.FragmentCustomizeBinding;

public class CustomizeFragment extends Fragment {

    private FragmentCustomizeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CustomizeViewModel customizeViewModel =
                new ViewModelProvider(this).get(CustomizeViewModel.class);

        binding = FragmentCustomizeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textCustomize;
        customizeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}