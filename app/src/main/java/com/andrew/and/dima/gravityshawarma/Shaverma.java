package com.andrew.and.dima.gravityshawarma;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Shaverma extends GameObject {
    Shaverma(float x, float y) {
        super(x, y, Constants.SHAVERMA_RADIUS);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.RED);
        canvas.drawCircle(x, y, r, paint);
    }
}
