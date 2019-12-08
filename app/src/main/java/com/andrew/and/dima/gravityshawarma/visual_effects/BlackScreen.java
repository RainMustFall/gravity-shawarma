package com.andrew.and.dima.gravityshawarma.visual_effects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.andrew.and.dima.gravityshawarma.map.Map;

public class BlackScreen extends VisualEffect {
  int framesNumber;
  int currentFrame;
  boolean finishing;
  Map listener;

  public BlackScreen(boolean finishing, int framesNumber, Map listener) {
    super(0, 0, 0);
    this.framesNumber = framesNumber;
    this.currentFrame = framesNumber;
    this.finishing = finishing;
    this.listener = listener;
  }

  @Override
  public boolean hasNext() {
    return currentFrame < framesNumber;
  }

  @Override
  public void next() {
    ++currentFrame;

    if (finishing && currentFrame == framesNumber) {
      listener.restartGame();
    }
  }

  @Override
  public void draw(Canvas canvas, Paint painter) {
    if (hasNext()) {
      if (finishing) {
        painter.setColor(Color.argb(255 / framesNumber * currentFrame, 0,0,0));
      } else {
        painter.setColor(Color.argb(255 - 255 / framesNumber * currentFrame, 0,0,0));
      }

      canvas.drawRect(listener.getScreenDimension(), painter);
    }
  }

  public void start() {
    currentFrame = 0;
  }
}
