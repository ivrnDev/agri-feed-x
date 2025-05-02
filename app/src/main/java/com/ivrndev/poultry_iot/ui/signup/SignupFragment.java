package com.ivrndev.poultry_iot.ui.signup;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.ivrndev.poultry_iot.MainActivity;
import com.ivrndev.poultry_iot.R;
import com.ivrndev.poultry_iot.databinding.FragmentSignupBinding;
import com.ivrndev.poultry_iot.domain.User;

public class SignupFragment extends Fragment {

    private FragmentSignupBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SignupViewModel signupViewModel = new ViewModelProvider(this).get(SignupViewModel
                .class);

        binding = FragmentSignupBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setupListener(signupViewModel);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupListener(SignupViewModel signupViewModel) {
        binding.submitBtn.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.scale_animation);
            v.startAnimation(animation);

            String name = binding.fullNameInput.getText().toString().trim();
            String username = binding.userNameInput.getText().toString().trim();
            String password = binding.passwordInput.getText().toString().trim();
            String confirmPassword = binding.confirmPasswordInput.getText().toString().trim();

            if (!validateInputs(name, username, password, confirmPassword)) {
                return;
            }

            User newUser = new User(name, username, password);
            signupViewModel.signup(newUser, exists -> {
                if (exists) {
                    binding.userNameLayout.setError("Username already exists");
                } else {
                    Toast.makeText(getContext(), "Account created successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
        });
    }

    private boolean validateInputs(String name, String email, String password, String confirmPassword) {
        boolean isValid = true;

        binding.fullNameInputLayout.setError(null);
        binding.userNameLayout.setError(null);
        binding.passwordLayout.setError(null);
        binding.confirmPasswordLayout.setError(null);

        if (name.isEmpty()) {
            binding.fullNameInputLayout.setError("Full name is required");
            isValid = false;
        } else if (name.length() < 3) {
            binding.fullNameInputLayout.setError("Full name must be at least 3 characters");
            isValid = false;
        } else if (name.length() > 30) {
            binding.fullNameInputLayout.setError("Full name cannot exceed 30 characters");
            isValid = false;
        } else if (!name.matches("[a-zA-Z ]+([.][a-zA-Z ]+)?")) {
            binding.fullNameInputLayout.setError("Full name can only contain letters and spaces");
            isValid = false;
        }

        if (email.isEmpty()) {
            binding.userNameLayout.setError("Username is required");
            isValid = false;
        } else if (email.length() < 4) {
            binding.userNameLayout.setError("Username must be at least 4 characters");
            isValid = false;
        } else if (email.length() > 30) {
            binding.userNameLayout.setError("Username cannot exceed 30 characters");
            isValid = false;
        }

        if (password.isEmpty()) {
            binding.passwordLayout.setError("Password is required");
            isValid = false;
        } else if (password.length() < 6) {
            binding.passwordLayout.setError("Password must be at least 6 characters");
            isValid = false;
        } else if (password.length() > 30) {
            binding.passwordLayout.setError("Password cannot exceed 30 characters");
            isValid = false;
        }

        if (confirmPassword.isEmpty()) {
            binding.confirmPasswordLayout.setError("Please confirm your password");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            binding.confirmPasswordLayout.setError("Passwords do not match");
            isValid = false;
        } else if (confirmPassword.length() > 30) {
            binding.confirmPasswordLayout.setError("Confirm password cannot exceed 30 characters");
            isValid = false;
        }

        return isValid;
    }
}
