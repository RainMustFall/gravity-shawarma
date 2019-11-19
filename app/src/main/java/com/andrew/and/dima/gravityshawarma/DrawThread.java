package com.andrew.and.dima.gravityshawarma;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

class DrawThread extends Thread {
    private Paint paint;
    private boolean running = false;
    private SurfaceHolder surfaceHolder;
    private GameSurface gameSurface;
    private Map map;

    public DrawThread(GameSurface newGameSurface) {
        map = new Map();
        gameSurface = newGameSurface;
        surfaceHolder = gameSurface.getHolder();

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

                canvas.drawColor(Color.BLACK);
                map.update(false);
                map.draw(canvas, paint);
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}