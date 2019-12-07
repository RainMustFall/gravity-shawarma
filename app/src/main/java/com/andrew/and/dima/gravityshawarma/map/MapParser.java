package com.andrew.and.dima.gravityshawarma.map;

import android.content.Context;

import com.andrew.and.dima.gravityshawarma.R;
import com.andrew.and.dima.gravityshawarma.game_object.BlackHole;
import com.andrew.and.dima.gravityshawarma.game_object.Planet;
import com.andrew.and.dima.gravityshawarma.game_object.Shaverma;
import com.andrew.and.dima.gravityshawarma.game_object.Spaceship;
import com.andrew.and.dima.gravityshawarma.json.JsonMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class MapParser {
  private Spaceship spaceship;
  private ArrayList<Planet> planets;
  private LinkedList<Shaverma> shavermas;
  private ArrayList<BlackHole> blackHoles;
  private float mapWidth;
  private float mapHeight;

  public MapParser(Integer mapNumber, Context context) {
    InputStream inputStream =
        context.getResources().openRawResource(R.raw.map0);
    String mapDataString = new Scanner(inputStream).useDelimiter("\\A").next();

    Gson gson = new Gson();
    JsonMap mapData = null;
    java.lang.reflect.Type mapDataType = new TypeToken<JsonMap>() {
    }.getType();
    mapData = gson.fromJson(mapDataString, mapDataType);

    spaceship = mapData.getSpaceship();
    planets = mapData.getPlanets();
    shavermas = mapData.getShavermas();
    blackHoles = mapData.getBlackHoles();
    mapWidth = mapData.getMapWidth();
    mapHeight = mapData.getMapHeight();
  }

  public Spaceship getSpaceship() {
    return spaceship;
  }

  public ArrayList<Planet> getPlanets() {
    return planets;
  }

  public LinkedList<Shaverma> getShavermas() {
    return shavermas;
  }

  public ArrayList<BlackHole> getBlackHoles() {
    return blackHoles;
  }

  public float getMapWidth() {
    return mapWidth;
  }

  public float getMapHeight() {
    return mapHeight;
  }
}
