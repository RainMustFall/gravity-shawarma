package com.andrew.and.dima.gravityshawarma;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Spaceship extends GameObject {
    private boolean touchedBlackHole;
    private FloatVector moveVector;

    Spaceship(float x, float y) {
        super(x, y, Constants.SPACESHIP_RADIUS);
        touchedBlackHole = false;
        moveVector = new FloatVector();
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLUE);
        canvas.drawCircle(x, y, r, paint);
    }

    void addToMoveVector(FloatVector acceleration) {
        moveVector.add(acceleration);
    }

    void updatePosition() {
        x += moveVector.x;
        y += moveVector.y;
    }

    public void setTouchedFlag(boolean touched) {
        touchedBlackHole = touched;
    }


    public boolean touchedHoleBefore() {
        return touchedBlackHole;
    }
}
