package com.andrew.and.dima.gravityshawarma;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Map {
    private Random randomGenerator;

    Spaceship spaceship;
    ArrayList<Planet> planets;
    LinkedList<Shaverma> shavermas;
    ArrayList<BlackHole> blackHoles;

    Map() {
        randomGenerator = new Random();

        planets = new ArrayList<>(Arrays.asList(
                new Planet(500, 700, 500),
                new Planet(500, 1600, 1000)
        ));

        shavermas = new LinkedList<>(Arrays.asList(
                new Shaverma(150, 510),
                new Shaverma(200, 750)
        ));

        blackHoles = new ArrayList<>(Arrays.asList(
                new BlackHole(400, 950),
                new BlackHole(400, 1150)
        ));

        spaceship = new Spaceship(1000, 900);
    }

    void update(boolean accelerate) {
        applyGravitation();
        collectShavermas();
        interactWithBlackHoles();
        spaceship.updatePosition();
    }

    private void interactWithBlackHoles() {
        boolean touchedOnThisStep = false;
        int curIndex = 0;

        for (BlackHole blackHole : blackHoles) {
            if (spaceship.touches(blackHole)) {
                if (!spaceship.touchedHoleBefore() && !touchedOnThisStep) {
                    int randomIndex = randomGenerator.nextInt(blackHoles.size() - 1);
                    if (randomIndex >= curIndex) {
                        ++randomIndex;
                    }

                    System.out.println(randomIndex + " " + curIndex);
                    blackHole.teleport(spaceship, blackHoles.get(randomIndex));
                }
                touchedOnThisStep = true;
            }
            ++curIndex;
        }

        spaceship.setTouchedFlag(touchedOnThisStep);
    }

        private void collectShavermas () {
            Iterator<Shaverma> iterator = shavermas.iterator();
            while (iterator.hasNext()) {
                if (spaceship.touches(iterator.next())) {
                    iterator.remove();
                }
            }
        }

        private void applyGravitation () {
            FloatVector deltaVector = new FloatVector();
            for (Planet planet : planets) {
                deltaVector.add(planet.calculateVector(spaceship));
            }
            spaceship.addToMoveVector(deltaVector);
        }

        public void draw (Canvas canvas, Paint paint){
            canvas.drawColor(Color.WHITE);

            for (Planet planet : planets) {
                planet.draw(canvas, paint);
            }

            for (Shaverma shaverma : shavermas) {
                shaverma.draw(canvas, paint);
            }

            for (BlackHole blackHole : blackHoles) {
                blackHole.draw(canvas, paint);
            }

            spaceship.draw(canvas, paint);
        }
    }
