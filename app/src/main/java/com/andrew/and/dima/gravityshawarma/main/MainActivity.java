package com.andrew.and.dima.gravityshawarma.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andrew.and.dima.gravityshawarma.map.MapActivity;
import com.andrew.and.dima.gravityshawarma.R;

public class MainActivity extends AppCompatActivity {
  static final int START_LEVEL = 1;

  private Button startButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    startButton = findViewById(R.id.start_button);

    startButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        intent.putExtra("mapNumber", 2);
        startActivityForResult(intent, START_LEVEL);
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode,
                                  Intent data) {
    if (requestCode == START_LEVEL) {
      Context context = getApplicationContext();
      int duration = Toast.LENGTH_SHORT;
      String text = (resultCode == RESULT_OK) ? "You won!" : "You lose!";
      Toast toast = Toast.makeText(context, text, duration);
      toast.show();
    }
    super.onActivityResult(requestCode, resultCode, data);
  }
}
