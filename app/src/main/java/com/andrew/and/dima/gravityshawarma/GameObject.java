package com.andrew.and.dima.gravityshawarma;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class GameObject {
    protected float x, y, r;

    GameObject(float newX, float newY, float newR) {
        x = newX;
        y = newY;
        r = newR;
    }

    public abstract void draw(Canvas canvas, Paint paint);

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getR() {
        return r;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean touches(GameObject another) {
        float dx = x - another.getX();
        float dy = y - another.getY();
        float dr = r + another.getR();
        return (dx * dx + dy * dy) <= dr * dr;
    }
}
