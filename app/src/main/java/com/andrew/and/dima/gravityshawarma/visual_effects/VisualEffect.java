package com.andrew.and.dima.gravityshawarma.visual_effects;

import com.andrew.and.dima.gravityshawarma.game_object.GameObject;

public abstract class VisualEffect extends GameObject {
  public VisualEffect(float x, float y, float radius) {
    super(x, y, radius);
  }

  public abstract void next();

  public abstract boolean hasNext();
}
