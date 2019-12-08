package com.andrew.and.dima.gravityshawarma.game_object;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.andrew.and.dima.gravityshawarma.utils.Constants;
import com.andrew.and.dima.gravityshawarma.utils.FloatVector;

public abstract class GameObject {
  public enum TextureType {
    BLACK_HOLE,
    PLANET_0,
    PLANET_1,
    PLANET_2,
    PLANET_3,
    PLANET_4,
    SHAVERMA,
    SPACESHIP,
    SPACESHIP_ACCELERATED
  }

  // Object coordinates on the abstract internal map. These coordinates point
  // to the center of the object (here we assume that all the objects are
  // circular). These values are initialized once (in the constructor) and
  // doesn't change later for the most derived objects (like planets).
  // Spaceship is an exception for that rule.
  protected float internalX;
  protected float internalY;

  // Radius of the object. Here we still assume that all the objects
  // are circular. This value doesn't change after initializing in the
  // constructor.
  protected float internalRadius;

  // Object coordinates on the real screen (depend on what part of map is shown
  // and etc). They are updated by MapView.
  protected float screenX;
  protected float screenY;
  protected float screenRadius;

  // This value is initialized in the constructors of derived classes.
  protected TextureType textureType;

  // Constructor initializes constant variables with passed values and
  // "screen" variables with zeros. Screen variables MUST BE updated by
  // MapView before you try to draw the object. You can use
  // updateScreenCoordinates method for that.
  public GameObject(float internalX, float internalY, float internalRadius) {
    this.internalX = internalX;
    this.internalY = internalY;
    this.internalRadius = internalRadius;
    this.screenX = 0;
    this.screenY = 0;
  }

  // This method is called by MapView periodically.
  public void updateScreenCoordinates(float screenX, float screenY) {
    this.screenX = screenX;
    this.screenY = screenY;
  }

  // This function checks if the current object intersects with the passed
  // another object. Internal coordinates (not screen) are used.
  public boolean touches(GameObject anotherObject) {
    float dx = internalX - anotherObject.internalX;
    float dy = internalY - anotherObject.internalY;
    float dr = internalRadius + anotherObject.internalRadius;
    return ((dx * dx + dy * dy) <= dr * dr);
  }

  // This abstract function draws an object on the passed canvas with use of
  // passed painter. Before drawing you MUST ensure that the object needs to
  // be shown, because it actual screen coordinates can be negative or bigger
  // than the size of the screen.
  public abstract void draw(Canvas canvas, Paint painter);

  public TextureType getTextureType() {
    return textureType;
  }

  public float getInternalX() {
    return internalX;
  }

  public float getInternalY() {
    return internalY;
  }

  public float getInternalRadius() {
    return internalRadius;
  }

  public float getScreenX() {
    return screenX;
  }

  public float getScreenY() {
    return screenY;
  }

  public float getScreenRadius() {
    return screenRadius;
  }

  public void setScreenX(float screenX) {
    this.screenX = screenX;
  }

  public void setScreenY(float screenY) {
    this.screenY = screenY;
  }

  public void setScreenRadius(float screenRadius) {
    this.screenRadius = screenRadius;
  }
}
