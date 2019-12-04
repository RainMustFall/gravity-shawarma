package com.andrew.and.dima.gravityshawarma.game_object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.andrew.and.dima.gravityshawarma.utils.Constants;

public class BlackHole extends GameObject {
  public BlackHole(float internalX, float internalY) {
    super(internalX, internalY, Constants.BLACK_HOLE_RADIUS, 0);
  }

  @Override
  public void draw(Canvas canvas, Paint painter) {
    painter.setColor(Color.BLACK);
    canvas.drawCircle(screenX, screenY, radius, painter);
  }
}
