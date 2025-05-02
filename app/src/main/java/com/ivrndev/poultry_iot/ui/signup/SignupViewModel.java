package com.ivrndev.poultry_iot.ui.signup;

import androidx.lifecycle.ViewModel;

import com.google.firebase.Firebase;
import com.ivrndev.poultry_iot.domain.User;
import com.ivrndev.poultry_iot.service.FirebaseService;

public class SignupViewModel extends ViewModel {
    private FirebaseService firebaseService;


    public SignupViewModel() {
        this.firebaseService = FirebaseService.getInstance();
    }

    public void signup(User user) {
        String path = "users/" + user.getUsername();
        if (user == null) return;
        firebaseService.writeData(path, user);
    }
}
