package com.example.HackathonApp_v02;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class StatsFragment extends Fragment {
    private XpViewModel currentXpLevel;


    public StatsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_stats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentXpLevel = new ViewModelProvider(requireActivity()).get(XpViewModel.class);
        TextView tvXp = view.findViewById(R.id.tvXp);
        TextView tvLevel = view.findViewById(R.id.tvLevel);

        Integer currentLevel = currentXpLevel.getXp().getValue().intValue()/500;
        tvXp.setText("XP UNTIL NEXT LEVEL: " + (500-(currentXpLevel.getXp().getValue().intValue()-currentLevel*500)));
        tvLevel.setText("CURRENT LEVEL: " + currentLevel);
    }
}