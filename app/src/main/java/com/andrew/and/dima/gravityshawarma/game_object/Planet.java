package com.andrew.and.dima.gravityshawarma.game_object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.andrew.and.dima.gravityshawarma.utils.Constants;

import java.util.Random;

public class Planet extends GameObject {
  public Planet(float internalX, float internalY, float mass) {
    super(internalX, internalY,
        Constants.RADIUS_COEFFICIENT * (float) Math.pow(mass, 1f / 3), mass);

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

  @Override
  public void draw(Canvas canvas, Paint painter) {
    painter.setColor(Color.GREEN);
    canvas.drawCircle(screenX, screenY, screenRadius, painter);
  }
}
