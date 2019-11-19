package com.andrew.and.dima.gravityshawarma;

public class FloatVector {
    public float x, y;

    FloatVector() {
        x = 0;
        y = 0;
    }

    FloatVector(float newX, float newY) {
        x = newX;
        y = newY;
    }

    public void add(FloatVector another) {
        x += another.x;
        y += another.y;
    }
}
