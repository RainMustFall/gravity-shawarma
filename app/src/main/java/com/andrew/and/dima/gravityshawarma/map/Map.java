package com.andrew.and.dima.gravityshawarma.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.andrew.and.dima.gravityshawarma.game_object.BlackHole;
import com.andrew.and.dima.gravityshawarma.game_object.GameObject;
import com.andrew.and.dima.gravityshawarma.game_object.Planet;
import com.andrew.and.dima.gravityshawarma.game_object.Shaverma;
import com.andrew.and.dima.gravityshawarma.game_object.Spaceship;
import com.andrew.and.dima.gravityshawarma.utils.Constants;
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
  private float pixelDensity = 0;

  private float offsetX;
  private float offsetY;

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
            new BlackHole(1550, 500)
    ));

    spaceship = new Spaceship(900, 1000);
  }

  public void setPixelDensity(float pixelDensity) {
    this.pixelDensity = pixelDensity;
  }

  public void setScreenSize(float screenWidthDp, float screenHeightDp) {
    this.screenWidthDp = screenWidthDp;
    this.screenHeightDp = screenHeightDp;
  }

  public void initOffsetCoordinates(float widthDp, float heightDp) {
    offsetX = widthDp / 2 - spaceship.getInternalX();
    offsetY = heightDp / 2 - spaceship.getInternalY();
  }

  // This function checks if interaction between the spaceship and shavermas /
  // black holes is necessary, performs it. After that it updates the
  // coordinates of the spaceship.
  public void updateInternalState(FloatVector accelerationOn) {
    updateSpaceshipState(accelerationOn);
    collectShavermas();
    interactWithPlanets();
    interactWithBlackHoles();
    spaceship.move();
  }

  // This function updates spaceship internalX, internalY coordinates
  // according to its move vector and current acceleration.
  private void updateSpaceshipState(FloatVector acceleration) {
    FloatVector deltaVector = new FloatVector(acceleration);

    if (!acceleration.isZero()) {
      spaceship.setAccelerated(true);
      // TODO: improve this logic. Reduce the fuel of the spaceship, etc.
    } else {
      spaceship.setAccelerated(false);
    }

    for (Planet planet : planets) {
      deltaVector.add(planet.calculateForceVector(
              spaceship.getInternalX(), spaceship.getInternalY()));
    }

    spaceship.addAccelerationToMoveVector(deltaVector);

    if (spaceship.getScreenX() < 0 ||
            spaceship.getScreenX() > screenWidthDp * pixelDensity ||
            spaceship.getScreenY() < 0 ||
            spaceship.getScreenY() > screenHeightDp * pixelDensity) {
      finishGame(false);
    }
  }

  // This function updates all the objects screen coordinates (screenX, screenY,
  // screenRadius) according to the real screen size.
  public void updateScreenCoordinates() {
    offsetX += spaceship.getMoveX() * Constants.MAP_OFFSET_COEFFICIENT;
    offsetY += spaceship.getMoveY() * Constants.MAP_OFFSET_COEFFICIENT;

    setScreenCoordinates(planets);
    setScreenCoordinates(blackHoles);
    setScreenCoordinates(shavermas);
    setScreenCoordinates(spaceship);
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

  private void setScreenCoordinates(List<? extends GameObject> objects) {
    for (GameObject object : objects) {
      setScreenCoordinates(object);
    }
  }

  private void setScreenCoordinates(GameObject object) {
    object.setScreenX(pixelDensity * (object.getInternalX() + offsetX));
    object.setScreenY(pixelDensity * (object.getInternalY() + offsetY));
    object.setScreenRadius(pixelDensity * object.getInternalRadius());
  }

  private void interactWithBlackHoles() {
    boolean touchedHole = false;
    for (int i = 0; i < blackHoles.size(); ++i) {
      touchedHole |= spaceship.touches(blackHoles.get(i));
      if (touchedHole && !spaceship.hasAlreadyTeleported()) {
        // TODO: improve random logic.
        int nextBlackHoleIndex = randomGenerator.nextInt(blackHoles.size());

        float teleportX = blackHoles.get(nextBlackHoleIndex).getInternalX()
                - blackHoles.get(i).getInternalX();
        float teleportY = blackHoles.get(nextBlackHoleIndex).getInternalY()
                - blackHoles.get(i).getInternalY();

        spaceship.teleport(teleportX, teleportY);

        offsetX -= teleportX;
        offsetY -= teleportY;
        break;
      }
    }
    spaceship.setAlreadyTeleported(touchedHole);
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
