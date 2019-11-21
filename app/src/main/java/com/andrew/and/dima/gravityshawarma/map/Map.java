package com.andrew.and.dima.gravityshawarma.map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import com.andrew.and.dima.gravityshawarma.game_object.BlackHole;
import com.andrew.and.dima.gravityshawarma.game_object.Planet;
import com.andrew.and.dima.gravityshawarma.game_object.Shaverma;
import com.andrew.and.dima.gravityshawarma.game_object.Spaceship;
import com.andrew.and.dima.gravityshawarma.utils.FloatVector;

public class Map {
  private Random randomGenerator;

  private Spaceship spaceship;
  private ArrayList<Planet> planets;
  private LinkedList<Shaverma> shavermas;
  private ArrayList<BlackHole> blackHoles;

  public Map() {
    randomGenerator = new Random();

    planets = new ArrayList<>(Arrays.asList(
        new Planet(700, 500, 500),
        new Planet(1600, 500, 1000)
    ));

    shavermas = new LinkedList<>(Arrays.asList(
        new Shaverma(510, 150),
        new Shaverma(750, 200)
    ));

    blackHoles = new ArrayList<>(Arrays.asList(
        new BlackHole(950, 400),
        new BlackHole(1150, 400)
    ));

    spaceship = new Spaceship(900, 1000);
  }

  public void update(boolean accelerate) {
    applyGravitation();
    collectShavermas();
    interactWithBlackHoles();
    spaceship.updatePosition();
  }

  private void interactWithBlackHoles() {
    boolean touchedOnThisStep = false;
    int currentIndex = 0;

    for (BlackHole blackHole : blackHoles) {
      if (spaceship.touches(blackHole)) {
        if (!spaceship.touchedHoleBefore() && !touchedOnThisStep) {
          int randomIndex = randomGenerator.nextInt(blackHoles.size() - 1);
          if (randomIndex >= currentIndex) {
            ++randomIndex;
          }

          System.out.println(randomIndex + " " + currentIndex);
          blackHole.teleport(spaceship, blackHoles.get(randomIndex));
        }
        touchedOnThisStep = true;
      }
      ++currentIndex;
    }

    spaceship.setTouchedFlag(touchedOnThisStep);
  }

  public void draw(Canvas canvas, Paint paint) {
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

  private void collectShavermas() {
    Iterator<Shaverma> iterator = shavermas.iterator();
    while (iterator.hasNext()) {
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
}
