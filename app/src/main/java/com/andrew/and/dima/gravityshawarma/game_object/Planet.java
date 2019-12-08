package com.andrew.and.dima.gravityshawarma.game_object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

import com.andrew.and.dima.gravityshawarma.utils.Constants;
import com.andrew.and.dima.gravityshawarma.utils.FloatVector;

public class Planet extends GameObject {
  // Physical mass of the object (to count gravity force in the game).
  // Initialized once in the constructor.
  protected float mass;

  public Planet(float internalX, float internalY, float mass) {
    super(internalX, internalY,
        Constants.RADIUS_COEFFICIENT * (float) Math.pow(mass, 1f / 3));
    this.mass = mass;

    Random random = new Random();
    int textureIndex = random.nextInt(5);
    if (textureIndex == 0) {
      textureType = TextureType.PLANET_0;
    } else if (textureIndex == 1) {
      textureType = TextureType.PLANET_1;
    } else if (textureIndex == 2) {
      textureType = TextureType.PLANET_2;
    } else if (textureIndex == 3) {
      textureType = TextureType.PLANET_3;
    } else if (textureIndex == 4) {
      textureType = TextureType.PLANET_4;
    }
  }

  // Returns Force vector to the object, located by passed coordinates. By
  // default uses internal coordinates and internalRadius values for that, but
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

  @Override
  public void draw(Canvas canvas, Paint painter) {
    painter.setColor(Color.GREEN);
    canvas.drawCircle(screenX, screenY, screenRadius, painter);
  }
}
