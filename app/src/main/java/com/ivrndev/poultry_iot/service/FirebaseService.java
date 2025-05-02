package com.ivrndev.poultry_iot.service;

import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    // Read data from a path
    public DatabaseReference getData(String path) {
        return database.child(path);
    }
}
