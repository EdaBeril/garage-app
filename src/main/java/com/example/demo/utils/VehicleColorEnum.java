package com.example.demo.utils;

import java.util.Arrays;

public enum VehicleColorEnum {
  YELLOW,
  BLUE,
  RED,
  WHITE,
  BLACK,
  ORANGE,
  GREEN,
  GRAY,
  PURPLE,
  BROWN;


  public static boolean isValidVehicleColor(String color) {
    return Arrays.stream(VehicleColorEnum.values()).map(VehicleColorEnum::toString).anyMatch(t -> t.equalsIgnoreCase(color));
  }
}
