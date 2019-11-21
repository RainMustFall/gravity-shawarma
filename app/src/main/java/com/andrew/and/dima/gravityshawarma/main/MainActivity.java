package com.andrew.and.dima.gravityshawarma.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.andrew.and.dima.gravityshawarma.map.MapViewActivity;
import com.andrew.and.dima.gravityshawarma.R;

public class MainActivity extends AppCompatActivity {
  private Button startButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    startButton = findViewById(R.id.start_button);

    startButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, MapViewActivity.class);
        startActivity(intent);
      }
    });
  }
}
