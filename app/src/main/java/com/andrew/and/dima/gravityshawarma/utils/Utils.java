package com.andrew.and.dima.gravityshawarma.utils;

import java.util.Random;

public class Utils {
  public static int getRandomExceptValue(int range, int exception,
                                         Random randomGenerator) {
    int answer = randomGenerator.nextInt(range - 1);
    if (answer >= exception) {
      ++answer;
    }
    return answer;
  }
}
