package com.example.demo.utils;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum VehicleTypeEnum {
  CAR(1, "Car"),
  JEEP(2, "Jeep"),
  TRUCK(4, "Truck"),
  UNDEFINED(0, "UNDEFINED");

  private String type;
  private Integer width;

  VehicleTypeEnum(Integer width, String type) {
    this.width = width;
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public static Integer getDescription(String vehicleType) {
    if (vehicleType.equalsIgnoreCase(CAR.type)) {
      return CAR.getValue();
    } else if (vehicleType.equalsIgnoreCase(JEEP.type)) {
      return JEEP.getValue();
    } else if (vehicleType.equalsIgnoreCase(TRUCK.type)) {
      return TRUCK.getValue();
    } else {
      return null;
    }
  }

  public Integer getValue() {
    return width;
  }

  public static boolean isValidVehicleType(String type) {
    return Arrays.stream(VehicleTypeEnum.values()).anyMatch(t -> t.getType().equals(type));
  }

  public static VehicleTypeEnum findVehicle(String type) {
    return Arrays.stream(VehicleTypeEnum.values()).filter(t -> t.getType().equals(type)).findFirst().orElse(UNDEFINED);
  }
}
