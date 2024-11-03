package com.example.HackathonApp_v02;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class MySecondaryProject {
    private static FirebaseApp INSTANCE;

    public static FirebaseApp getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = getSecondProject(context);
        }
        return INSTANCE;
    }

    private static FirebaseApp getSecondProject(Context context) {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setProjectId("greenpanion-android-final")
                .setApplicationId("1:1037078388175:android:011bd9f0aadf2ea55346ba")
                .setApiKey("AIzaSyDiwFxv-fJSI8PzWfVK2UTqVNUUpEU_c9Y")
                .build();

        FirebaseApp.initializeApp(context, options, "secondary");
        return FirebaseApp.getInstance("secondary");
    }
}