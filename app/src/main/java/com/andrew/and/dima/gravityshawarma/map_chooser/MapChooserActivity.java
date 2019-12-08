package com.andrew.and.dima.gravityshawarma.map_chooser;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MapChooserActivity extends AppCompatActivity {
  private MapChooser mapChooser;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mapChooser = new MapChooser(this);
  }

}
