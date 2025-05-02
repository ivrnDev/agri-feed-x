package com.ivrndev.poultry_iot.ui.signup;


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
            String email = binding.userNameInput.getText().toString().trim();
            String password = binding.passwordInput.getText().toString().trim();


        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Please fill up all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

            User newUser = new User(name, email, password);
            signupViewModel.signup(newUser);


        });
    }

}
