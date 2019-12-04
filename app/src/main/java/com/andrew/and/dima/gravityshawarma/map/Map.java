package com.andrew.and.dima.gravityshawarma.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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

  private final float MAP_WIDTH = 2000;
  private final float MAP_HEIGHT = 1200;

  private float screenWidthDp = 0;
  private float screenHeightDp = 0;

  private boolean finished = false;
  private boolean finishedState;

  public Map() {
    randomGenerator = new Random();

    planets = new ArrayList<>(Arrays.asList(
        new Planet(700, 200, 500),
        new Planet(700, 500, 500),
        new Planet(800, 600, 500),
        new Planet(900, 700, 500),
        new Planet(1000, 600, 500),
        new Planet(1100, 500, 500),
        new Planet(1200, 400, 500),
        new Planet(1300, 250, 500),
        new Planet(1400, 400, 500),
        new Planet(1500, 500, 1000),
        new Planet(1700, 500, 700),
        new Planet(1900, 500, 700),
        new Planet(400, 500, 1000),
        new Planet(400, 700, 500),
        new Planet(400, 800, 1000),
        new Planet(400, 1000, 1000)
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

  public void setScreenSize(float screenWidthDp, float screenHeightDp) {
    this.screenWidthDp = screenWidthDp;
    this.screenHeightDp = screenHeightDp;
  }

  // This function checks if interaction between the spaceship and shavermas /
  // black holes is necessary, performs it. After that it updates the
  // coordinates of the spaceship.
  public void updateInternalState(boolean accelerationOn) {
    updateSpaceshipState(accelerationOn);
    collectShavermas();
    interactWithPlanets();
    interactWithBlackHoles();
    spaceship.move();
  }

  // This function updates spaceship internalX, internalY coordinates
  // according to its move vector and current acceleration.
  private void updateSpaceshipState(boolean accelerationOn) {
    FloatVector deltaVector = new FloatVector();

    for (Planet planet : planets) {
      deltaVector.add(planet.calculateForceVector(
          spaceship.getInternalX(), spaceship.getInternalY()));
    }

    if (accelerationOn) {
      spaceship.setAccelerated(true);
      // TODO: improve this logic. Reduce the fuel of the spaceship, etc.
      spaceship.addAccelerationToMoveVector(new FloatVector(1, 1));
    } else {
      spaceship.setAccelerated(false);
    }

    spaceship.addAccelerationToMoveVector(deltaVector);

    if (spaceship.getInternalX() <= 0 ||
        spaceship.getInternalX() >= MAP_WIDTH ||
        spaceship.getInternalY() <= 0 ||
        spaceship.getInternalY() >= MAP_HEIGHT) {
      finishGame(false);
    }
  }

  // This function updates all the objects screen coordinates (screenX, screenY,
  // screenRadius) according to the real screen size.
  public void updateScreenCoordinates(float pixelDensity) {
    float halfOfScreenWidthDp = screenWidthDp / 2;
    float halfOfScreenHeightDp = screenHeightDp / 2;

    if (spaceship.getInternalX() - halfOfScreenWidthDp > 0 &&
        spaceship.getInternalX() + halfOfScreenWidthDp < MAP_WIDTH) {
      spaceship.setScreenX(halfOfScreenWidthDp);
    } else {
      spaceship.setScreenX(spaceship.getInternalX());
    }

    if (spaceship.getInternalY() - halfOfScreenHeightDp > 0 &&
        spaceship.getInternalY() + halfOfScreenHeightDp < MAP_HEIGHT) {
      spaceship.setScreenY(halfOfScreenHeightDp);
    } else {
      spaceship.setScreenY(spaceship.getInternalY());
    }

    float offsetX = spaceship.getScreenX() - spaceship.getInternalX();
    float offsetY = spaceship.getScreenY() - spaceship.getInternalY();

    for (Planet planet : planets) {
      planet.setScreenX(pixelDensity * (planet.getInternalX() + offsetX));
      planet.setScreenY(pixelDensity * (planet.getInternalY() + offsetY));
    }

    for (BlackHole blackHole : blackHoles) {
      blackHole.setScreenX(pixelDensity * (blackHole.getInternalX() + offsetX));
      blackHole.setScreenY(pixelDensity * (blackHole.getInternalY() + offsetY));
    }

    for (Shaverma shaverma : shavermas) {
      shaverma.setScreenX(pixelDensity * (shaverma.getInternalX() + offsetX));
      shaverma.setScreenY(pixelDensity * (shaverma.getInternalY() + offsetY));
    }

    spaceship.setScreenX(pixelDensity * spaceship.getScreenX());
    spaceship.setScreenY(pixelDensity * spaceship.getScreenY());
  }

  private void collectShavermas() {
    Iterator<Shaverma> iterator = shavermas.iterator();
    while (iterator.hasNext()) {
      if (spaceship.touches(iterator.next())) {
        // TODO: improve this logic. Increase the shavermas counter, check if
        // TODO: all shavermas are collected, etc.
        iterator.remove();
      }
    }
  }

  private void interactWithBlackHoles() {
    for (int i = 0; i < blackHoles.size(); ++i) {
      if (spaceship.touches(blackHoles.get(i))) {
        // TODO: improve random logic.
        int nextBlackHoleIndex =
            randomGenerator.nextInt(blackHoles.size());
        spaceship.teleport(
            blackHoles.get(i).getInternalX(),
            blackHoles.get(i).getInternalY(),
            blackHoles.get(nextBlackHoleIndex).getInternalX(),
            blackHoles.get(nextBlackHoleIndex).getInternalY());
        break;
      }
    }
  }

  private void interactWithPlanets() {
    for (Planet planet : planets) {
      if (spaceship.touches(planet)) {
//        finishGame(false);
//        break;
      }
    }
  }

  private void finishGame(boolean win) {
    finished = true;
    finishedState = win;
    System.out.println("finish");
  }

  public boolean hasGameFinished() {
    return finished;
  }

  public boolean getFinishedState() {
    return finishedState;
  }

  public Spaceship getSpaceship() {
    return spaceship;
  }

  public List<Planet> getPlanetsList() {
    return planets;
  }

  public List<Shaverma> getShavermasList() {
    return shavermas;
  }

  public List<BlackHole> getBlackHoleList() {
    return blackHoles;
  }
}
