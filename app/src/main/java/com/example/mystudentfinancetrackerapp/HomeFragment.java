package com.example.mystudentfinancetrackerapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.core.view.ViewCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import com.example.mystudentfinancetrackerapp.databinding.ActivityDashboardBinding;
import android.widget.TextView;

public class HomeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Toast.makeText(getActivity(), "WELCOME TO HOME", Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_home, container, false);


    }
}