package com.andrew.and.dima.gravityshawarma.game_object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.andrew.and.dima.gravityshawarma.utils.Constants;

public class BlackHole extends GameObject {
  public BlackHole(float x, float y) {
    super(x, y, Constants.BLACK_HOLE_RADIUS);
  }

  @Override
  public void draw(Canvas canvas, Paint paint) {
    paint.setColor(Color.BLACK);
    canvas.drawCircle(x, y, r, paint);
  }

  public void teleport(Spaceship spaceship, BlackHole another) {
    float dx = spaceship.getX() - x;
    float dy = spaceship.getY() - y;

    spaceship.setX(another.x + dx);
    spaceship.setY(another.y + dy);
  }
}
