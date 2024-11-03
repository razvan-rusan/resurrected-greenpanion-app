package com.example.HackathonApp_v02;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HowToRecycleFragment extends Fragment {
    private XpViewModel currentXp;

    public HowToRecycleFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_how_to_recycle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentXp = new ViewModelProvider(requireActivity()).get(XpViewModel.class);
        //currentXp.zero();


        Button btScanQRCode = view.findViewById(R.id.verifyCode);
        btScanQRCode.setOnClickListener(view1 -> {
            scanCode();
        });
    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to turn on flashlight!");
        options.setBeepEnabled(false);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    private void parseScanningResultAndAddStats(String results) {
        List<String> numbers = Arrays.asList(results.split(","));
        FirebaseFirestore db2 = FirebaseFirestore.getInstance(MySecondaryProject.getInstance(requireContext()));
        FirebaseUser user2 = FirebaseAuth.getInstance(MySecondaryProject.getInstance(requireContext())).getCurrentUser();


        Integer metal = Integer.parseInt(numbers.get(0));
        Integer plastic = Integer.parseInt(numbers.get(1));
        Integer paper = Integer.parseInt(numbers.get(2));
        Map<String, Object> recycled = new HashMap<String, Object>();
        recycled.put("email", user2.getEmail());
        recycled.put("metal", metal);
        recycled.put("plastic", plastic);
        recycled.put("paper", paper);

        //update xp and levels
        //1 item worth 100 xp
        //500 xp is worth a level
        db2.collection("levelling_and_xp").whereEqualTo("email", user2.getEmail()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                addAndSaveUserXp(metal*100 + plastic*100 + paper*100);
                Log.v("RAZVNTAG", "User xp updated!");
            }
        });

        Toast.makeText(requireContext(), "Recycled items updated!\n Metal: " + numbers.get(0) + " Plastic: " + numbers.get(1) + " Paper: " + numbers.get(2),Toast.LENGTH_LONG).show();


        Log.v("RAZVANTAG", "db2 1");
        db2.collection("recycled_items").whereEqualTo("email",user2.getEmail()).get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> userDocsWithEmail = task.getResult().getDocuments();
                            try {
                                Map<String,Object> data = userDocsWithEmail.get(0).getData();
                                Integer metal = ((Long) data.get("metal")).intValue();
                                Integer plastic = ((Long) data.get("plastic")).intValue();
                                Integer paper = ((Long) data.get("paper")).intValue();

                                recycled.put("metal",new Long(((Integer)recycled.get("metal"))+metal));
                                recycled.put("plastic",new Long(((Integer)recycled.get("plastic"))+metal));
                                recycled.put("paper", new Long(((Integer)recycled.get("paper"))+paper));

                                userDocsWithEmail.get(0).getReference().set(recycled).addOnCompleteListener(
                                        new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.v("RAZVANTAG", "Recycled items successfully saved in user with current email!");
                                                }
                                            }
                                        }
                                );
                            } catch (IndexOutOfBoundsException e) {
                                db2.collection("recycled_items").add(recycled).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        if (task.isSuccessful()) {
                                            Log.v("RAZVANTAG","New user with current recyled items succesfully added!");
                                        }
                                    }
                                });
                            }

                        }
                    }
                }
        );
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            parseScanningResultAndAddStats(result.getContents());
            Log.v("RAZVANTAG", "Added stats to db!");
        }
    });

    private void addAndSaveUserXp(Integer added_xp) {
        FirebaseFirestore db2 = FirebaseFirestore.getInstance(MySecondaryProject.getInstance(requireContext()));
        FirebaseUser user2 = FirebaseAuth.getInstance(MySecondaryProject.getInstance(requireContext())).getCurrentUser();


        Map<String, Object> user_map = new HashMap<String, Object>();
        user_map.put("email", user2.getEmail());
        user_map.put("xp", currentXp.getXp().getValue().intValue()+added_xp);

        Log.v("RAZVANTAG", "db2 2");
        db2.collection("levelling_and_xp").whereEqualTo("email",user2.getEmail()).get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            //Log.v("RAZVNTAG", "db2 2 oncomplete");
                            List<DocumentSnapshot> userDocsWithEmail = task.getResult().getDocuments();
                            Log.v("USER_EMAIL", userDocsWithEmail.toString());
                            try {
                                Log.v("RAZVNTAG", "db2 2 oncomplete try");
                                Log.v("RAZVANTAG", userDocsWithEmail.get(0).toString());
                                userDocsWithEmail.get(0).getReference().set(user_map).addOnCompleteListener(
                                        new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.v("RAZVANTAG", "Xp successfully saved in user with current email!");
                                                }
                                            }
                                        }
                                );
                            } catch (IndexOutOfBoundsException e) {
                                Log.v("RAZVNTAG", "db2 2 oncomplete catch");
                                db2.collection("levelling_and_xp").add(user_map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        if (task.isSuccessful()) {
                                            Log.v("RAZVANTAG","New user with current email succesfully added!");
                                        } else {
                                            Log.v("RAZVANTAG","Failed to add new user with current email :( !");
                                        }
                                    }
                                });
                            }

                        }
                    }
                }
        );
    }
}