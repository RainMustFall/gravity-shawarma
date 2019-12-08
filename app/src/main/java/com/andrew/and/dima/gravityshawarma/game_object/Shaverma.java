package com.andrew.and.dima.gravityshawarma.game_object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.andrew.and.dima.gravityshawarma.utils.Constants;

public class Shaverma extends GameObject {
  public Shaverma(float internalX, float internalY) {
    super(internalX, internalY, Constants.SHAVERMA_RADIUS, 0);
    textureType = TextureType.SHAVERMA;
  }

  @Override
  public void draw(Canvas canvas, Paint painter) {
    painter.setColor(Color.RED);
    canvas.drawCircle(screenX, screenY, screenRadius, painter);
  }
}
