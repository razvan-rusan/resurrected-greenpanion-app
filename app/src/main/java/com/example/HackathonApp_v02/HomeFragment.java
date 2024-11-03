package com.example.HackathonApp_v02;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment {

    private XpViewModel currentXp;
    private Button button;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentXp = new ViewModelProvider(requireActivity()).get(XpViewModel.class);

        Button showPrizesAndGuide = view.findViewById(R.id.showPrizes);
        Button showMap = view.findViewById(R.id.showMap);
        Button showStats = view.findViewById(R.id.showStats);
        Button showRecycle = view.findViewById(R.id.whatToRecycle);
        Button logout = view.findViewById(R.id.logoutButton);

        showPrizesAndGuide.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_prizesAndGuideFragment);
        });

        showMap.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_mapFragment);
        });

        showStats.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_statsFragment);
        });

        showRecycle.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_howToRecycleFragment);
        });

        logout.setOnClickListener(view1 -> {
            FirebaseAuth.getInstance(MySecondaryProject.getInstance(requireContext())).signOut();
            Toast.makeText(requireContext(), "Successfully logged out!", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_loginFragment);
        });

        retrieveUserXp();

        fillDbWithRandomStuff();
    }

    private void retrieveUserXp() {
        FirebaseFirestore db2 = FirebaseFirestore.getInstance(MySecondaryProject.getInstance(requireContext()));
        FirebaseUser user2 = FirebaseAuth.getInstance(MySecondaryProject.getInstance(requireContext())).getCurrentUser();

        db2.collection("levelling_and_xp")
                .whereEqualTo("email", user2.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) /*throws IndexOutOfBoundsException*/ {
                        if (task.isSuccessful())
                            Log.v("RAZVANTAG", "User with given email retrieved succesfully!");
                        QuerySnapshot querySnapshot = task.getResult();
                        List<DocumentSnapshot> docs = querySnapshot.getDocuments();
                        if (docs.size()>=1) {
                            Map<String, Object> user_data = docs.get(0).getData();
                            currentXp.setXp((Number) user_data.get("xp"));
                        } else {
                            currentXp.setXp(0);
                        }
                    }
                });

    }


    private void fillDbWithRandomStuff() {
        Log.v("RAZVANTAG", "Filling db with random stuff!");

        Map<String, Object> user1 = new HashMap<String, Object>();
        user1.put("email" ,"malina@woohoo.com");
        user1.put("xp", 2300);

        Map<String, Object> user2 = new HashMap<String, Object>();
        user2.put("email", "iulia@woohoo.com");
        user2.put("xp", 200);

        Map<String, Object> user3 = new HashMap<String, Object>();
        user3.put("email", "matei@woohoo.com");
        user3.put("xp", 200);

        Map<String, Object> user4 = new HashMap<String, Object>();
        user4.put("email", "sergiu@woohoo.com");
        user4.put("xp", 1000);

        Map<String, Object> user5 = new HashMap<String, Object>();
        user5.put("email", "iulian@woohoo.com");
        user5.put("xp", 200);

        Map<String, Object> user6 = new HashMap<String, Object>();
        user6.put("email","rusanrazvan@gmail.com");
        user6.put("xp",200);

        FirebaseFirestore db2 = FirebaseFirestore.getInstance(MySecondaryProject.getInstance(requireContext()));

        List<Map<String, Object>> users = Arrays.asList(user1,user2,user3,user4,user5,user6);

        int idx=0;
        for (Map<String, Object> user : users) {
            db2.collection("levelling_and_xp").document("user"+idx).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.v("RAZVANTAG", "Another value added!");
                    }
                }
            });

            idx++;
        }


    }
}