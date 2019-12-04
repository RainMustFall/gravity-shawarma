package com.andrew.and.dima.gravityshawarma.game_object;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.andrew.and.dima.gravityshawarma.utils.Constants;
import com.andrew.and.dima.gravityshawarma.utils.FloatVector;

public abstract class GameObject {
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
  protected float radius;

  // Physical mass of the object (to count gravity force in the game).
  // Initialized once in the constructor. Can be equal to zero for some
  // objects.
  protected float mass;

  // Object coordinates on the real screen (depend on what part of map is shown
  // and etc). They are updated by MapView.
  protected float screenX;
  protected float screenY;

  // Constructor initializes constant variables with passed values and
  // "screen" variables with zeros. Screen variables MUST BE updated by
  // MapView before you try to draw the object. You can use
  // updateScreenCoordinates method for that.
  public GameObject(float internalX, float internalY, float radius,
                    float mass) {
    this.internalX = internalX;
    this.internalY = internalY;
    this.radius = radius;
    this.mass = mass;
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
    float dr = radius + anotherObject.radius;
    return ((dx * dx + dy * dy) <= dr * dr);
  }

  // Returns Force vector to the object, located by passed coordinates. By
  // default uses internal coordinates and radius values for that, but
  // this behaviour can be overridden in derived classes.
  public FloatVector calculateForceVector(float anotherObjectX,
                                          float anotherObjectY) {
    if (mass == 0) return new FloatVector(0, 0);

    float dx = internalX - anotherObjectX;
    float dy = internalY - anotherObjectY;
    float distance = (float) Math.sqrt(dx * dx + dy * dy);

    float power = mass * Constants.GRAVITY_COEFFICIENT / (dx * dx + dy * dy);

    return new FloatVector(dx / distance * power, dy / distance * power);
  }

  // This abstract function draws an object on the passed canvas with use of
  // passed painter. Before drawing you MUST ensure that the object needs to
  // be shown, because it actual screen coordinates can be negative or bigger
  // than the size of the screen.
  public abstract void draw(Canvas canvas, Paint painter);

  public float getInternalX() {
    return internalX;
  }

  public float getInternalY() {
    return internalY;
  }

  public float getRadius() {
    return radius;
  }

  public float getScreenX() {
    return screenX;
  }

  public float getScreenY() {
    return screenY;
  }

  public void setScreenX(float screenX) {
    this.screenX = screenX;
  }

  public void setScreenY(float screenY) {
    this.screenY = screenY;
  }
}
