package com.andrew.and.dima.gravityshawarma.map_chooser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.andrew.and.dima.gravityshawarma.R;
import com.andrew.and.dima.gravityshawarma.map.MapActivity;

public class MapChooserActivity extends AppCompatActivity {
  static final int START_GAME = 1;

//  private MapChooser mapChooser;

  private Button map1Button;
  private Button map2Button;
  private Button map3Button;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

//    mapChooser = new MapChooser(this);

    setContentView(R.layout.activity_map_chooser);
    map1Button = findViewById(R.id.map1_button);
    map2Button = findViewById(R.id.map2_button);
    map3Button = findViewById(R.id.map3_button);

    map1Button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(MapChooserActivity.this, MapActivity.class);
        intent.putExtra("mapNumber", 1);
        startActivityForResult(intent, START_GAME);
      }
    });

    map2Button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(MapChooserActivity.this, MapActivity.class);
        intent.putExtra("mapNumber", 2);
        startActivityForResult(intent, START_GAME);
      }
    });

    map3Button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(MapChooserActivity.this, MapActivity.class);
        intent.putExtra("mapNumber", 3);
        startActivityForResult(intent, START_GAME);
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode,
                                  Intent data) {
    if (requestCode == START_GAME) {
      Context context = getApplicationContext();
      int duration = Toast.LENGTH_SHORT;
      String text = (resultCode == RESULT_OK) ? "You won!" : "You lose!";
      Toast toast = Toast.makeText(context, text, duration);
      toast.show();
    }
    super.onActivityResult(requestCode, resultCode, data);
  }
}
