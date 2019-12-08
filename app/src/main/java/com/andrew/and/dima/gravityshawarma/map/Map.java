package com.andrew.and.dima.gravityshawarma.map;

import android.content.Context;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.andrew.and.dima.gravityshawarma.game_object.BlackHole;
import com.andrew.and.dima.gravityshawarma.game_object.GameObject;
import com.andrew.and.dima.gravityshawarma.game_object.Planet;
import com.andrew.and.dima.gravityshawarma.game_object.Shaverma;
import com.andrew.and.dima.gravityshawarma.game_object.Spaceship;
import com.andrew.and.dima.gravityshawarma.utils.FloatVector;
import com.andrew.and.dima.gravityshawarma.utils.OffsetGenerator;
import com.andrew.and.dima.gravityshawarma.utils.Utils;
import com.andrew.and.dima.gravityshawarma.visual_effects.BlackScreen;
import com.andrew.and.dima.gravityshawarma.visual_effects.Explosion;
import com.andrew.and.dima.gravityshawarma.visual_effects.ShavermaEffect;
import com.andrew.and.dima.gravityshawarma.visual_effects.VisualEffect;

public class Map {
  private Random randomGenerator;

  private Spaceship spaceship;
  private ArrayList<Planet> planets;
  private LinkedList<Shaverma> shavermas;
  private LinkedList<VisualEffect> effects;
  private ArrayList<BlackHole> blackHoles;

  private float mapWidth;
  private float mapHeight;

  private float pixelDensity = 0;

  private float offsetX;
  private float offsetY;

  private OffsetGenerator xGenerator;
  private OffsetGenerator yGenerator;

  private boolean finished = false;
  private boolean finishedState;

  private FloatVector deltaVector = new FloatVector();

  private MapParser mapParser;

  private BlackScreen innerScreen;
  private BlackScreen outerScreen;

  private int mapId;
  private Context context;

  public Map(int mapId, Context context) {
    this.mapId = mapId;
    this.context = context;

    randomGenerator = new Random();
    innerScreen = new BlackScreen(false, 10, this);
    outerScreen = new BlackScreen(true, 10, this);
    restartGame();
  }

  public void restartGame() {
    mapParser = new MapParser(mapId, context);

    effects = new LinkedList<>();
    spaceship = mapParser.getSpaceship();
    planets = mapParser.getPlanets();
    shavermas = mapParser.getShavermas();
    blackHoles = mapParser.getBlackHoles();
    mapWidth = mapParser.getMapWidth();
    mapHeight = mapParser.getMapHeight();

    innerScreen.start();
  }

  public void setPixelDensity(float pixelDensity) {
    this.pixelDensity = pixelDensity;
  }

  // Shown part of map is defined by the offsets (from the point 0,0 of the
  // internal map). Offset generators generate offsets to make the spaceship
  // movements smooth (depending on its location and acceleration).
  // We assume that screen size doesn't change, and initialize offset
  // generators only once.
  public void initOffsetGenerators(float widthDp, float heightDp) {
    xGenerator = new OffsetGenerator(
        new FloatVector(0, 0),
        new FloatVector(mapWidth, widthDp),
        new FloatVector(spaceship.getInternalX(), widthDp / 2));

    yGenerator = new OffsetGenerator(
        new FloatVector(0, 0),
        new FloatVector(mapHeight, heightDp),
        new FloatVector(spaceship.getInternalY(), heightDp / 2));
  }

  // This function checks if interaction between the spaceship and shavermas /
  // black holes is necessary, performs it. After that it updates the
  // coordinates of the spaceship.
  public void updateInternalState(FloatVector accelerationOn) {
    if (spaceship.getAliveState()) {
      updateSpaceshipState(accelerationOn);
      collectShavermas();
      interactWithPlanets();
      interactWithBlackHoles();
    }

    updateEffects();
    spaceship.move();
  }

  // This function updates spaceship internalX, internalY coordinates
  // according to its move vector and current acceleration.
  private void updateSpaceshipState(FloatVector acceleration) {
    deltaVector = new FloatVector(acceleration);

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

    if (spaceship.getInternalX() < 0 ||
        spaceship.getInternalX() > mapWidth ||
        spaceship.getInternalY() < 0 ||
        spaceship.getInternalY() > mapHeight) {
      spaceship.setAliveState(false);
      finishGame(false);
    }
  }

  // This function updates all the objects screen coordinates (screenX, screenY,
  // screenRadius) according to the real screen size.
  public void updateScreenCoordinates() {
    if (xGenerator == null) {
      return;
    }

    offsetX = xGenerator.getFunction(
        spaceship.getInternalX()) - spaceship.getInternalX();
    offsetY = yGenerator.getFunction(
        spaceship.getInternalY()) - spaceship.getInternalY();

    setScreenCoordinates(planets);
    setScreenCoordinates(blackHoles);
    setScreenCoordinates(shavermas);
    setScreenCoordinates(spaceship);
    setScreenCoordinates(effects);
  }

  private void collectShavermas() {
    Iterator<Shaverma> iterator = shavermas.iterator();
    while (iterator.hasNext()) {
      Shaverma shaverma = iterator.next();
      if (spaceship.touches(shaverma)) {
        effects.add(new ShavermaEffect(shaverma));
        iterator.remove();
      }
    }

    if (shavermas.isEmpty()) {
      finishGame(true);
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
        int nextBlackHoleIndex = Utils.getRandomExceptValue(
            blackHoles.size(), i, randomGenerator);

        float teleportX = blackHoles.get(nextBlackHoleIndex).getInternalX()
            - blackHoles.get(i).getInternalX();
        float teleportY = blackHoles.get(nextBlackHoleIndex).getInternalY()
            - blackHoles.get(i).getInternalY();

        spaceship.teleport(teleportX, teleportY);

        xGenerator.setNewBreak(spaceship.getInternalX(),
            spaceship.getScreenX() / pixelDensity);

        yGenerator.setNewBreak(spaceship.getInternalY(),
            spaceship.getScreenY() / pixelDensity);
        break;
      }
    }
    spaceship.setAlreadyTeleported(touchedHole);
  }

  private void interactWithPlanets() {
    for (Planet planet : planets) {
      if (spaceship.touches(planet)) {
        // Explosion object will notify the map when the animation is over
        // and the game should be finished
        effects.add(new Explosion(spaceship, this));
        spaceship.setAliveState(false);
        break;
      }
    }
  }

  private void updateEffects() {
    Iterator<VisualEffect> iterator = effects.iterator();
    while (iterator.hasNext()) {
      VisualEffect effect = iterator.next();
      if (effect.hasNext()) {
        effect.next();
      } else {
        iterator.remove();
      }
    }

    if (innerScreen.hasNext()) {
      innerScreen.next();
    }

    if (outerScreen.hasNext()) {
      outerScreen.next();
    }
  }

  public void finishGame(boolean win) {
    if (win) {
      finished = true;
      finishedState = true;
    } else {
      // This object will restart the game once the animation is over
      outerScreen.start();
    }
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

  public LinkedList<VisualEffect> getEffects() {
    return effects;
  }

  public Rect getScreenDimension() {
    return new Rect(0, 0, (int)(mapWidth * pixelDensity), (int)(mapHeight * pixelDensity));
  }

  public BlackScreen getInnerScreen() {
    return innerScreen;
  }

  public BlackScreen getOuterScreen() {
    return outerScreen;
  }
}
