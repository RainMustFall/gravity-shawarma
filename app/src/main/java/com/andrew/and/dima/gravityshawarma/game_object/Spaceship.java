package com.andrew.and.dima.gravityshawarma.game_object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.andrew.and.dima.gravityshawarma.utils.Constants;
import com.andrew.and.dima.gravityshawarma.utils.FloatVector;

public class Spaceship extends GameObject {
  protected FloatVector moveVector;

  // The color is now used to represent the accelerated and non-accelerated
  // states of the spaceship. It will be later replaced with the textures.
  protected int spaceshipColor = Color.BLUE;

  public Spaceship(float internalX, float internalY) {
    super(internalX, internalY, Constants.SPACESHIP_RADIUS, 0);
    moveVector = new FloatVector(0, 0);
  }

  @Override
  public void draw(Canvas canvas, Paint painter) {
    painter.setColor(spaceshipColor);
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

  public void setAccelerated(boolean state) {
    if (state) {
      spaceshipColor = Color.YELLOW;
    } else {
      spaceshipColor = Color.BLUE;
    }
  }
}
