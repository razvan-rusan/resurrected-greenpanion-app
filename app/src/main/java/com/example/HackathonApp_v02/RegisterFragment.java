package com.example.HackathonApp_v02;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterFragment extends Fragment {

    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button registerButton;

    private FirebaseAuth auth;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(requireContext(), "Account successfully registered!", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_loginFragment);
                } else {
                    Toast.makeText(requireContext(), "Account registration failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etEmail = view.findViewById(R.id.registerEmail);
        etPassword = view.findViewById(R.id.registerPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);
        registerButton = view.findViewById(R.id.buttonRegister);


        registerButton.setEnabled(false);

        TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String username = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();
                registerButton.setEnabled(!username.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        etEmail.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);
        etConfirmPassword.addTextChangedListener(textWatcher);


        registerButton.setOnClickListener(view1 -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String confirmPassword = etConfirmPassword.getText().toString().trim();
            auth = FirebaseAuth.getInstance(MySecondaryProject.getInstance(requireContext()));


            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Hey! Please provide both an email and password!", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 6) {
                Toast.makeText(requireContext(), "Hey! Please write a password that's at least 6 characters long.", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(requireContext(), "Hey! Please make sure passwords match!", Toast.LENGTH_SHORT).show();
            }
            else {
                registerUser(email, password);
            }
        });
    }
}