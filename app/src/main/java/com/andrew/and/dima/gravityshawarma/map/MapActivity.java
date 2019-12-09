package com.andrew.and.dima.gravityshawarma.map;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.andrew.and.dima.gravityshawarma.game_object.BlackHole;
import com.andrew.and.dima.gravityshawarma.game_object.Planet;
import com.andrew.and.dima.gravityshawarma.game_object.Shaverma;
import com.andrew.and.dima.gravityshawarma.utils.Constants;
import com.andrew.and.dima.gravityshawarma.utils.FloatVector;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MapActivity extends AppCompatActivity {
  private MapView mapView;
  private Map map;

  // Timer, which controls the mapView, invalidates it every N milliseconds.
  Timer mapViewUpdateTimer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mapView = new MapView(this);
    setContentView(mapView);

    Integer mapNumber =
        Objects.requireNonNull(getIntent().getExtras()).getInt("mapNumber");
    int mapId = getResources().getIdentifier("map" + mapNumber.toString(),
        "raw", getPackageName());
    map = new Map(mapId, this);

    mapView.post(new Runnable() {
      @Override
      public void run() {
        mapView.updateSize();
        map.setPixelDensity(mapView.getPixelDensity());
        map.initOffsetGenerators(mapView.getWidthDp(), mapView.getHeightDp());
      }
    });

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

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mapViewUpdateTimer.cancel();
  }

  private void onTimerEvent() {
    mapView.updateSize();
    map.updateInternalState(mapView.getAcceleration());
    map.updateScreenCoordinates();
    mapView.invalidate();

    if (map.hasGameFinished()) {
      if (map.getFinishedState()) {
        setResult(Activity.RESULT_OK);
      } else {
        setResult(Activity.RESULT_CANCELED);
      }
      finish();
    }
  }

  class MapView extends View {
    private Paint painter;

    private float pixelDensity;
    private float widthDp;
    private float heightDp;

    private FloatVector acceleration;

    public MapView(Context context) {
      super(context);
      acceleration = new FloatVector();
      painter = new Paint();
      updateSize();
    }

    public synchronized void updateSize() {
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

    public FloatVector getAcceleration() {
      return acceleration;
    }

    @Override
    protected void onDraw(Canvas canvas) {
      canvas.drawColor(Color.WHITE);

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
      if (event.getAction() == MotionEvent.ACTION_DOWN
          || event.getAction() == MotionEvent.ACTION_MOVE) {
        float dx = event.getX() - getWidth() / 2f;
        float dy = event.getY() - getHeight() / 2f;

        float length = (float) Math.sqrt(dx * dx + dy * dy);
        acceleration.x = dx / length * Constants.ACCELERATION_COEFFICIENT;
        acceleration.y = dy / length * Constants.ACCELERATION_COEFFICIENT;
      } else {
        acceleration.x = 0;
        acceleration.y = 0;
      }
      return true;
    }
  }
}
