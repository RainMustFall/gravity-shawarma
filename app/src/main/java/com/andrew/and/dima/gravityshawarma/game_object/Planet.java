package com.andrew.and.dima.gravityshawarma.game_object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.andrew.and.dima.gravityshawarma.utils.Constants;
import com.andrew.and.dima.gravityshawarma.utils.FloatVector;

public class Planet extends GameObject {
  private float mass;

  public Planet(float x, float y, float mass) {
    super(x, y, Constants.RADIUS_COEFFICIENT * (float) Math.pow(mass, 1f / 3));
    this.mass = mass;
  }

  @Override
  public void draw(Canvas canvas, Paint paint) {
    paint.setColor(Color.GREEN);
    canvas.drawCircle(x, y, r, paint);
  }

  public FloatVector calculateVector(Spaceship spaceship) {
    float dx = x - spaceship.getX();
    float dy = y - spaceship.getY();
    float length = (float) Math.sqrt(dx * dx + dy * dy);

    float power = mass * Constants.GRAVITY_COEFFICIENT / (dx * dx + dy * dy);

    return new FloatVector(dx / length * power, dy / length * power);
  }
}
