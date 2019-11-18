package com.andrew.and.dima.gravityshawarma;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public abstract class GameObject {
    protected Point position;

    GameObject(Point newPosition) {
        position = newPosition;
    }

    public abstract void draw(Canvas canvas, Paint paint);
}
