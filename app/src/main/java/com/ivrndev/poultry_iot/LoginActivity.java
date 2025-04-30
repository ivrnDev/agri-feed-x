package com.ivrndev.poultry_iot;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ivrndev.poultry_iot.ui.login.LoginFragment;


public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.loginContainer, new LoginFragment())
                    .commit();
        }
    }
}
