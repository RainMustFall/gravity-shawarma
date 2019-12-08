package com.andrew.and.dima.gravityshawarma.map_chooser;

import android.content.Context;

import com.andrew.and.dima.gravityshawarma.R;
import com.andrew.and.dima.gravityshawarma.json.JsonMapArray;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MapChooser {
  public enum MapType {
    FINISHED, OPENED, CLOSED, NOT_AVAILABLE
  }

  private ArrayList<MapType> availableMaps;

  public MapChooser(Context context) {
    InputStream inputStream =
        context.getResources().openRawResource(R.raw.available_maps);
    String availableMapsString =
        new Scanner(inputStream).useDelimiter("\\A").next();

    Gson gson = new Gson();
    JsonMapArray availableMapsData = null;
    java.lang.reflect.Type mapDataType = new TypeToken<JsonMapArray>() {
    }.getType();
    availableMapsData = gson.fromJson(availableMapsString, mapDataType);

    availableMaps = availableMapsData.getAvailableMaps();
  }

  public ArrayList<MapType> getAvailableMaps() {
    return availableMaps;
  }
}
