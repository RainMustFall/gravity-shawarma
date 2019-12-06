package com.andrew.and.dima.gravityshawarma.utils;

public class OffsetGenerator {
  private FloatVector leftPoint;
  private FloatVector rightPoint;
  private FloatVector middlePoint;

  public OffsetGenerator(FloatVector leftPoint, FloatVector rightPoint, FloatVector middlePoint) {
    this.leftPoint = leftPoint;
    this.rightPoint = rightPoint;
    this.middlePoint = middlePoint;
  }

  public float getFunction(float argument) {
    if (argument < middlePoint.x) {
      return (argument - leftPoint.x)
              / (middlePoint.x - leftPoint.x)
              * (middlePoint.y - leftPoint.y)
              + leftPoint.y;
    }
    return (argument - middlePoint.x)
            / (rightPoint.x - middlePoint.x)
            * (rightPoint.y - middlePoint.y)
            + middlePoint.y;
  }

  public void setNewBreak(float x, float y) {
    middlePoint.x = x;
    middlePoint.y = y;
  }
}
