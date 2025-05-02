package com.ivrndev.poultry_iot.ui.login;


import android.content.Intent;
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

import com.ivrndev.poultry_iot.MainActivity;
import com.ivrndev.poultry_iot.R;
import com.ivrndev.poultry_iot.databinding.FragmentLoginBinding;
import com.ivrndev.poultry_iot.domain.User;

import java.util.HashMap;
import java.util.Map;

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

            String username = binding.usernameInput.getText().toString().trim();
            String password = binding.passwordInput.getText().toString().trim();

            boolean hasError = false;

            binding.userNameLayout.setError(null);
            binding.passwordLayout.setError(null);

            if (username.isEmpty()) {
                binding.userNameLayout.setError("Username is required");
                hasError = true;
            }

            if (password.isEmpty()) {
                binding.passwordLayout.setError("Password is required");
                hasError = true;
            }

            if (hasError) return;

            User user = new User("", username, password);
            loginViewModel.login(user, getContext(), exists -> {
                if (exists) {
                    Toast.makeText(getContext(), "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    Toast.makeText(getContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
