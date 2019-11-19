package com.andrew.and.dima.gravityshawarma;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Planet extends GameObject {
    private float mass;

    Planet(float x, float y, float newMass) {
        super(x, y, Constants.RADIUS_COEFFICIENT * (float) Math.pow(newMass, 1f / 3));
        mass = newMass;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawCircle(x, y, r, paint);
    }

    public FloatVector calculateVector(Spaceship spaceship) {
        float dx = x - spaceship.getX();
        float dy = y - spaceship.getY();
        float length = (float)Math.sqrt(dx * dx + dy * dy);

        float power = mass * Constants.GRAVITY_COEFFICIENT / (dx * dx + dy * dy);

        return new FloatVector(dx / length * power, dy / length * power);
    }
}
