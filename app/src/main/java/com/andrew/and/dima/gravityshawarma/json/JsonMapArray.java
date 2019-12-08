package com.andrew.and.dima.gravityshawarma.json;

import com.andrew.and.dima.gravityshawarma.map_chooser.MapChooser;

import java.util.ArrayList;

public class JsonMapArray {
  private ArrayList<MapChooser.MapType> availableMaps;

  public ArrayList<MapChooser.MapType> getAvailableMaps() {
    return availableMaps;
  }
}
