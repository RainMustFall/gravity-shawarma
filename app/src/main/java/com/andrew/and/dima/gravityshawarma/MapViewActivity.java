package com.andrew.and.dima.gravityshawarma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MapViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameSurface(this));
    }
}
