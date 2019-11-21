package com.andrew.and.dima.gravityshawarma.game_object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.andrew.and.dima.gravityshawarma.utils.Constants;

public class Shaverma extends GameObject {
  public Shaverma(float x, float y) {
    super(x, y, Constants.SHAVERMA_RADIUS);
  }

  @Override
  public void draw(Canvas canvas, Paint paint) {
    paint.setColor(Color.RED);
    canvas.drawCircle(x, y, r, paint);
  }
}
