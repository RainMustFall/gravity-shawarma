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

  private float INTERNAL_WIDTH = 1600;
  private float INTERNAL_HEIGHT = 800;

  private float SCREEN_WIDTH = 0;
  private float SCREEN_HEIGHT = 0;

  private boolean finished = false;
  private boolean finishedState;

  public Map() {
    randomGenerator = new Random();

    planets = new ArrayList<>(Arrays.asList(
        new Planet(700, 500, 500),
        new Planet(1500, 500, 1000)
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

  public void setScreenSize(float screenWidth, float screenHeight) {
    SCREEN_WIDTH = screenWidth;
    SCREEN_HEIGHT = screenHeight;
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

  // This function updates all the objects screen coordinates (screenX, screenY,
  // screenRadius) according to the real screen size.
  public void updateScreenState() {
    // TODO
  }

  // This function takes spaceship's position as a center of the current shown
  // part of the map and updates all the objects coordinates with necessary
  // offsets. Coordinates of the objects, which won't be shown on the screen,
  // can become negative or too big for the screen, and that's expected
  // behaviour (MapView will draw only objects with normal coordinates).
  public void moveScreenCoordinates() {
    // TODO
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
      // TODO: improve this logic. Reduce the fuel of the spaceship, etc.
      spaceship.addAccelerationToMoveVector(new FloatVector(1, 1));
    }

    spaceship.addAccelerationToMoveVector(deltaVector);
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
        finishGame(false);
        break;
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
