package com.andrew.and.dima.gravityshawarma.game_object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.andrew.and.dima.gravityshawarma.utils.Constants;

public class Planet extends GameObject {
  public Planet(float internalX, float internalY, float mass) {
    super(internalX,
        internalY,
        Constants.RADIUS_COEFFICIENT * (float) Math.pow(mass, 1f / 3),
        mass);
  }

  @Override
  public void draw(Canvas canvas, Paint painter) {
    painter.setColor(Color.GREEN);
    canvas.drawCircle(screenX, screenY, screenRadius, painter);
  }
}
