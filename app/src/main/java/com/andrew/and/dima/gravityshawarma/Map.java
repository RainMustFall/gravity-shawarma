package com.andrew.and.dima.gravityshawarma;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class Map {
    Spaceship spaceship;
    ArrayList<Planet> planets;
    LinkedList<Shaverma> shavermas;

    Map() {
        planets = new ArrayList<>(Arrays.asList(
                new Planet(500, 200, 500),
                new Planet(500, 900, 1000)
        ));

        shavermas = new LinkedList<>(Arrays.asList(
                new Shaverma(10, 10),
                new Shaverma(200, 450)
        ));

        spaceship = new Spaceship(1000, 400);
    }

    void update(boolean accelerate) {
        applyGravitation();
        collectShavermas();
        spaceship.updatePosition();
    }

    private void collectShavermas() {
        Iterator<Shaverma> iterator = shavermas.iterator();
        while(iterator.hasNext()) {
            if (spaceship.touches(iterator.next())) {
                iterator.remove();
            }
        }
    }

    private void applyGravitation() {
        FloatVector deltaVector = new FloatVector();
        for (Planet planet : planets) {
            deltaVector.add(planet.calculateVector(spaceship));
        }
        spaceship.addToMoveVector(deltaVector);
    }

    public void draw(Canvas canvas, Paint paint) {
        for (Planet planet : planets) {
            planet.draw(canvas, paint);
        }

        for (Shaverma shaverma : shavermas) {
            shaverma.draw(canvas, paint);
        }

        spaceship.draw(canvas, paint);
    }
}
