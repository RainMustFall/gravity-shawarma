package com.andrew.and.dima.gravityshawarma;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Planet extends GameObject {
    private double mass;
    private double radius;
    private final double RADIUS_COEFFICIENT = 20;

    Planet(double newMass, Point newPosition) {
        super(newPosition);
        mass = newMass;
        radius = Math.pow(mass, 1. / 3) * RADIUS_COEFFICIENT;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawCircle(position.x, position.y, (float)radius, paint);
    }
}
