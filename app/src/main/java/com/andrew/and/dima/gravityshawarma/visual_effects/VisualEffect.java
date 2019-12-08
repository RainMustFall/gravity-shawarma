package com.andrew.and.dima.gravityshawarma.visual_effects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.andrew.and.dima.gravityshawarma.game_object.GameObject;

public class VisualEffect extends GameObject {
  int iterations = 10;
  int currectIteration = 0;

  public VisualEffect(float x, float y, float radius) {
    super(x, y, radius);
  }

  @Override
  public void draw(Canvas canvas, Paint painter) {
    painter.setColor(Color.YELLOW);
    canvas.drawCircle(screenX, screenY, screenRadius, painter);
  }

  public void next() {
    ++currectIteration;
    internalRadius += 10;
  }

  public boolean hasNext() {
    return currectIteration < iterations;
  }
}
