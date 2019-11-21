package com.andrew.and.dima.gravityshawarma.map;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.andrew.and.dima.gravityshawarma.utils.GameSurface;

public class MapViewActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(new GameSurface(this));
  }
}
