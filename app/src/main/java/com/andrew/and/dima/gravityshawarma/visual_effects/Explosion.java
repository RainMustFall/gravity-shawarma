package com.andrew.and.dima.gravityshawarma.visual_effects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.andrew.and.dima.gravityshawarma.game_object.Shaverma;
import com.andrew.and.dima.gravityshawarma.game_object.Spaceship;
import com.andrew.and.dima.gravityshawarma.map.Map;

public class Explosion extends VisualEffect {
  Map listener;

  int currentIteration = 0;
  int iterations = 10;

  int transparency = 255;

  public Explosion(Spaceship spaceship, Map map) {
    super(spaceship.getInternalX(), spaceship.getInternalY(), spaceship.getInternalRadius());
    listener = map;
  }

  @Override
  public void next() {
    internalRadius += 10;
    transparency -= 255 / iterations;
    ++currentIteration;

    if (currentIteration == iterations) {
      listener.finishGame(false);
    }
  }

  @Override
  public boolean hasNext() {
    return currentIteration < iterations;
  }

  @Override
  public void draw(Canvas canvas, Paint painter) {
    painter.setColor(Color.argb(transparency, 44, 57, 129));
    canvas.drawCircle(screenX, screenY, screenRadius, painter);
  }
}
