package com.example.mystudentfinancetrackerapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.ViewCompat;
public class AddFragment extends Fragment {

    BottomNavigationView bottomNavigationView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Toast.makeText(getActivity(), "ADD FINANCES", Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_add, container, false);



    }
}