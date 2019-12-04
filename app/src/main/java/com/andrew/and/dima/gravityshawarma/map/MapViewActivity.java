package com.andrew.and.dima.gravityshawarma.map;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import com.andrew.and.dima.gravityshawarma.game_object.BlackHole;
import com.andrew.and.dima.gravityshawarma.game_object.Planet;
import com.andrew.and.dima.gravityshawarma.game_object.Shaverma;

import java.util.Timer;
import java.util.TimerTask;

public class MapViewActivity extends AppCompatActivity {
  private MapView mapView;
  private Map map;

  // Timer, which controls the mapView, invalidates it every N milliseconds.
  Timer mapViewUpdateTimer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mapView = new MapView(this);
    setContentView(mapView);

    map = new Map();

    mapViewUpdateTimer = new Timer();
    TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            onTimerEvent();
          }
        });
      }
    };
    mapViewUpdateTimer.scheduleAtFixedRate(timerTask, 0, 25);
  }

  private void onTimerEvent() {
    mapView.updateSize();
    map.updateInternalState(false);
    map.setScreenSize(mapView.getWidthDp(), mapView.getHeightDp());
    map.updateScreenCoordinates(mapView.getPixelDensity());
    mapView.invalidate();
  }

  class MapView extends View {
    private Paint painter;

    private float pixelDensity;
    private float widthDp;
    private float heightDp;

    public MapView(Context context) {
      super(context);
      painter = new Paint();
      updateSize();
    }

    public void updateSize() {
      pixelDensity = getResources().getDisplayMetrics().density;
      widthDp = getWidth() / pixelDensity;
      heightDp = getHeight() / pixelDensity;
    }

    public float getPixelDensity() {
      return pixelDensity;
    }

    public float getWidthDp() {
      return widthDp;
    }

    public float getHeightDp() {
      return heightDp;
    }

    @Override
    protected void onDraw(Canvas canvas) {
      canvas.drawColor(Color.WHITE);

      // TODO: draw only objects, which coordinates are inside the screen
      // system of the coordinates (positive, not bigger than the screen size).

      for (Planet planet : map.getPlanetsList()) {
        planet.draw(canvas, painter);
      }

      for (Shaverma shaverma : map.getShavermasList()) {
        shaverma.draw(canvas, painter);
      }

      for (BlackHole blackHole : map.getBlackHoleList()) {
        blackHole.draw(canvas, painter);
      }

      map.getSpaceship().draw(canvas, painter);
    }
  }
}
