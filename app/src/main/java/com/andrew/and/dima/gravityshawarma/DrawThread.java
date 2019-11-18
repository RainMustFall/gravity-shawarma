package com.andrew.and.dima.gravityshawarma;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;

class DrawThread extends Thread {
    private Planet planet;
    private Paint paint;
    private boolean running = false;
    private SurfaceHolder surfaceHolder;
    private GameSurface gameSurface;

    public DrawThread(GameSurface newGameSurface) {
        gameSurface = newGameSurface;
        surfaceHolder = gameSurface.getHolder();

        planet = new Planet(500, new Point(200, 300));
        paint = new Paint();
        paint.setColor(Color.GREEN);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        Canvas canvas;
        while (running) {
            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas(null);
                if (canvas == null)
                    continue;
            canvas.drawColor(Color.WHITE);
            planet.draw(canvas, paint);

            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}