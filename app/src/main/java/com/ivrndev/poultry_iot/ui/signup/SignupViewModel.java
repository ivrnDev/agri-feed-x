package com.ivrndev.poultry_iot.ui.signup;

import androidx.lifecycle.ViewModel;

import com.google.firebase.Firebase;
import com.ivrndev.poultry_iot.domain.User;
import com.ivrndev.poultry_iot.service.FirebaseService;

import java.util.function.Consumer;

public class SignupViewModel extends ViewModel {
    private FirebaseService firebaseService;

    public SignupViewModel() {
        this.firebaseService = FirebaseService.getInstance();
    }

    public void signup(User user, Consumer<Boolean> callback) {
        if (user == null) return;

        String path = "users/" + user.getUsername();
        firebaseService.checkExistent(path, exists -> {
            if (!exists) {
                firebaseService.writeData(path, user);
            }
            callback.accept(exists);
        });
    }
}
