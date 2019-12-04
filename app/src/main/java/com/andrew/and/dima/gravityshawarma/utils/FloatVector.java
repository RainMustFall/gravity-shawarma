package com.andrew.and.dima.gravityshawarma.utils;

public class FloatVector {
  public float x;
  public float y;

  public FloatVector() {
    x = 0;
    y = 0;
  }

  public FloatVector(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public void add(FloatVector another) {
    x += another.x;
    y += another.y;
  }
}
