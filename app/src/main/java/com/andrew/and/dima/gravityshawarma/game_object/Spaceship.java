package com.andrew.and.dima.gravityshawarma.game_object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.andrew.and.dima.gravityshawarma.utils.Constants;
import com.andrew.and.dima.gravityshawarma.utils.FloatVector;

public class Spaceship extends GameObject {
  protected FloatVector moveVector;

  public Spaceship(float internalX, float internalY) {
    super(internalX, internalY, Constants.SPACESHIP_RADIUS, 0);
    moveVector = new FloatVector(0, 0);
  }

  @Override
  public void draw(Canvas canvas, Paint painter) {
    painter.setColor(Color.BLUE);
    canvas.drawCircle(screenX, screenY, radius, painter);
  }

  public void addAccelerationToMoveVector(FloatVector acceleration) {
    moveVector.add(acceleration);
  }

  public void move() {
    internalX += moveVector.x;
    internalY += moveVector.y;
  }

  public void teleport(float currentBlackHoleX, float currentBlackHoleY,
                       float nextBlackHoleX, float nextBlackHoleY) {
    internalX += nextBlackHoleX - currentBlackHoleX;
    internalY += nextBlackHoleY - currentBlackHoleY;
  }
}
