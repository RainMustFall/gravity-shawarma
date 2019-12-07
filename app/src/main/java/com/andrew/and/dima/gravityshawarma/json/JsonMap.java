package com.andrew.and.dima.gravityshawarma.json;

import com.andrew.and.dima.gravityshawarma.game_object.BlackHole;
import com.andrew.and.dima.gravityshawarma.game_object.Planet;
import com.andrew.and.dima.gravityshawarma.game_object.Shaverma;
import com.andrew.and.dima.gravityshawarma.game_object.Spaceship;

import java.util.ArrayList;
import java.util.LinkedList;

public class JsonMap {
  private JsonGameObject spaceship;
  private ArrayList<JsonGameObjectWithMass> planets;
  private ArrayList<JsonGameObject> shavermas;
  private ArrayList<JsonGameObject> blackHoles;
  private float mapWidth;
  private float mapHeight;

  public float getMapWidth() {
    return mapWidth;
  }

  public float getMapHeight() {
    return mapHeight;
  }

  public Spaceship getSpaceship() {
    return new Spaceship(spaceship.x, spaceship.y);
  }

  public ArrayList<Planet> getPlanets() {
    ArrayList<Planet> planetsList = new ArrayList<>();
    for (JsonGameObjectWithMass object : planets) {
      planetsList.add(new Planet(object.x, object.y, object.mass));
    }
    return planetsList;
  }

  public LinkedList<Shaverma> getShavermas() {
    LinkedList<Shaverma> shavermasList = new LinkedList<>();
    for (JsonGameObject object : shavermas) {
      shavermasList.add(new Shaverma(object.x, object.y));
    }
    return shavermasList;
  }

  public ArrayList<BlackHole> getBlackHoles() {
    ArrayList<BlackHole> blackHolesList = new ArrayList<>();
    for (JsonGameObject object : blackHoles) {
      blackHolesList.add(new BlackHole(object.x, object.y));
    }
    return blackHolesList;
  }
}
