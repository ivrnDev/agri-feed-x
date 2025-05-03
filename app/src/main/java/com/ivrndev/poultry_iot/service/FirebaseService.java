package com.ivrndev.poultry_iot.service;

import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ivrndev.poultry_iot.domain.User;

import java.util.function.Consumer;

public class FirebaseService {
    private static FirebaseService instance;
    private final DatabaseReference database;

    private FirebaseService() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    public static synchronized FirebaseService getInstance() {
        if (instance == null) {
            instance = new FirebaseService();
        }
        return instance;
    }

    public void writeData(String path, Object data) {
        database.child(path).setValue(data)
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Data written to: " + path);
                })
                .addOnFailureListener(e -> {
                    System.err.println("Failed to write data: " + e.getMessage());
                });
    }

    public DatabaseReference getData(String path) {
        return database.child(path);
    }

    public void checkExistent(String path, Consumer<Boolean> callback) {
        database.child(path).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                boolean exists = task.getResult().exists();
                callback.accept(exists);
            } else {
                callback.accept(false);
            }
        });
    }

    public void hasUser(User user, Consumer<Boolean> callback) {
        database.child("users").child(user.getUsername()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                if (snapshot.exists()) {
                    String storedPassword = snapshot.child("password").getValue(String.class);
                    if (user.getPassword().equals(storedPassword)) {
                        callback.accept(true); // Match
                        return;
                    }
                }
            }
            callback.accept(false);
        });
    }
}
