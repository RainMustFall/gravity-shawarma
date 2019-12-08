package com.andrew.and.dima.gravityshawarma.game_object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.andrew.and.dima.gravityshawarma.utils.Constants;
import com.andrew.and.dima.gravityshawarma.utils.FloatVector;

public class Spaceship extends GameObject {
  protected float maxFuel;
  protected float currentFuel;

  protected FloatVector moveVector;

  // The color is now used to represent the accelerated and non-accelerated
  // states of the spaceship. It will be later replaced with the textures.
  protected int spaceshipColor = Color.BLUE;
  protected boolean alreadyTeleported = false;

  public Spaceship(float internalX, float internalY, float maxFuel) {
    super(internalX, internalY, Constants.SPACESHIP_RADIUS, 0);
    this.maxFuel = maxFuel;
    this.currentFuel = maxFuel;
    moveVector = new FloatVector(0, 0);
    textureType = TextureType.SPACESHIP;
  }

  @Override
  public void draw(Canvas canvas, Paint painter) {
    painter.setColor(spaceshipColor);
    canvas.drawCircle(screenX, screenY, screenRadius, painter);
  }

  public void addAccelerationToMoveVector(FloatVector acceleration) {
    moveVector.add(acceleration);
  }

  public void move() {
    internalX += moveVector.x;
    internalY += moveVector.y;
  }

  public void teleport(float teleportX, float teleportY) {
    internalX += teleportX;
    internalY += teleportY;
  }

  public void setAccelerated(boolean state) {
    if (state) {
      spaceshipColor = Color.YELLOW;
    } else {
      spaceshipColor = Color.rgb(44, 57, 129);
    }
  }

  public void setAlreadyTeleported(boolean alreadyTeleported) {
    this.alreadyTeleported = alreadyTeleported;
  }

  public boolean hasAlreadyTeleported() {
    return alreadyTeleported;
  }

  public float getMoveX() {
    return moveVector.x;
  }

  public float getMoveY() {
    return moveVector.y;
  }

  public float getMaxFuel() {
    return maxFuel;
  }

  public float getCurrentFuel() {
    return currentFuel;
  }
}
