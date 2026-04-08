package com.example.firebaseapp.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseUtil {
    private static final FirebaseAuth auth = FirebaseAuth.getInstance();
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static FirebaseAuth auth() {
        return auth;
    }

    public static FirebaseFirestore db() {
        return db;
    }
}