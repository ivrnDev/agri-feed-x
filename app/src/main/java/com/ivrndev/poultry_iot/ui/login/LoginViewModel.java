package com.ivrndev.poultry_iot.ui.login;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.ViewModel;

import com.ivrndev.poultry_iot.domain.User;
import com.ivrndev.poultry_iot.service.FirebaseService;

import java.util.function.Consumer;

public class LoginViewModel extends ViewModel {
    private FirebaseService firebaseService;

    public LoginViewModel() {
        this.firebaseService = FirebaseService.getInstance();
    }

    public void login(User user, Context context, Consumer<Boolean> callback) {
        if (user == null) return;

        String path = "users/" + user.getUsername();
        firebaseService.checkExistent(path, exists -> {
            if (exists) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", user.getUsername());
                editor.apply();
            }
            callback.accept(exists);
        });
    }

}
