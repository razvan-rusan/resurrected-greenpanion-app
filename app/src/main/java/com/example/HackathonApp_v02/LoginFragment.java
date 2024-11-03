package com.example.HackathonApp_v02;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment {


    private FirebaseAuth auth;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button buttonLogin = getView().findViewById(R.id.buttonRegister);
        buttonLogin.setEnabled(false);
        EditText editTextUser = getView().findViewById(R.id.registerEmail);
        EditText editTextPassword = getView().findViewById(R.id.registerPassword);

        auth = FirebaseAuth.getInstance(MySecondaryProject.getInstance(requireContext()));

        TextView tvRegister = getView().findViewById(R.id.tvRegisterHere);

        tvRegister.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
        });

        TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String username = editTextUser.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                buttonLogin.setEnabled(!username.isEmpty() && !password.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        editTextUser.addTextChangedListener(textWatcher);
        editTextPassword.addTextChangedListener(textWatcher);

        buttonLogin.setOnClickListener(view1 -> {
            String email = editTextUser.getText().toString();
            String password = editTextPassword.getText().toString();

            Log.v("RAZVANTAG", email + " login " + password);

            loginUser(email, password);
        });

    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Log.v("RAZVANTAG", "Succ!");
                Toast.makeText(requireActivity(), "Login succesful", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_homeFragment);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance(MySecondaryProject.getInstance(requireContext())).getCurrentUser();

        if (user != null) {
            Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_homeFragment);
        }
    }
}