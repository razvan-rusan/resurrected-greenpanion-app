package com.example.HackathonApp_v02;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class WelcomeFragment extends Fragment {

    public WelcomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button loginButton = view.findViewById(R.id.buttonLogin);
        Button signupButton = view.findViewById(R.id.buttonSignUp);

        loginButton.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_loginFragment);
        });

        signupButton.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_registerFragment);
        });
    }
}