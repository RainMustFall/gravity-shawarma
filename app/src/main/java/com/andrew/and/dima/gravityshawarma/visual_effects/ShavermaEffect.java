package com.andrew.and.dima.gravityshawarma.visual_effects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.andrew.and.dima.gravityshawarma.game_object.Shaverma;

public class ShavermaEffect extends VisualEffect {
  int currentIteration = 0;
  int iterations = 10;

  int transparency = 255;

  public ShavermaEffect(Shaverma shaverma) {
    super(shaverma.getInternalX(), shaverma.getInternalY(),
        shaverma.getInternalRadius());
  }

  @Override
  public void next() {
    internalRadius += 10;
    transparency -= 255 / iterations;
    ++currentIteration;
  }

  @Override
  public boolean hasNext() {
    return currentIteration < iterations;
  }

  @Override
  public void draw(Canvas canvas, Paint painter) {
    painter.setColor(Color.argb(transparency, 255, 0, 0));
    canvas.drawCircle(screenX, screenY, screenRadius, painter);
  }
}
