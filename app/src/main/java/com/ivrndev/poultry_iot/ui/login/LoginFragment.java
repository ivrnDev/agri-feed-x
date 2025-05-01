package com.ivrndev.poultry_iot.ui.login;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ivrndev.poultry_iot.R;
import com.ivrndev.poultry_iot.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel
                .class);

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupListener(loginViewModel);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupListener(LoginViewModel loginViewModel) {
        binding.submitBtn.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.scale_animation);
            v.startAnimation(animation);
        });
    }


}
