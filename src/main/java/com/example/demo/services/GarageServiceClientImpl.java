package com.example.demo.services;

import com.example.demo.dto.Garage;
import com.example.demo.dto.GarageRequest;
import com.example.demo.dto.StatusResponse;
import com.example.demo.dto.Vehicle;
import com.example.demo.utils.VehicleColorEnum;
import com.example.demo.utils.VehicleTypeEnum;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class GarageServiceClientImpl implements GarageServiceClient {

  @Autowired
  private Garage garage;

  public Integer park(GarageRequest request) {

    log.warn("New Garage Request Processing : " + request);
    int emptySlotIndex;

    VehicleTypeEnum vehicleEnum = VehicleTypeEnum.findVehicle(request.getVehicle().getVehicleType());

    if (!validateVehicle(request.getVehicle())) {
      log.info("Vehicle is not identified.");
      return -1;
    }

    emptySlotIndex = getEmptySlotIndex(vehicleEnum.getWidth());
    if (emptySlotIndex == -1) {
      log.info("Garage is full.");
      return -1;
    }
    fillSlot(garage.getGarageMap(), request.getVehicle(), vehicleEnum.getWidth(), emptySlotIndex);
    log.info("Allocated " + vehicleEnum.getWidth() + " slot");

    log.warn("New Garage Request Ended. Ticket Id : " + request.getVehicle());
    return emptySlotIndex;
  }

  public String status() {
    StringBuilder resultBuilder = new StringBuilder();
    Map<String, StatusResponse> statusList = new LinkedHashMap<>();

    if (garage.getGarageMap().size() == 0) {
      log.warn("Garage is empty");
      return "Garage is empty";
    }
    for (int i = 1; i <= 10; i++) {
      Vehicle vehicle = garage.getGarageMap().get(i);
      if (vehicle == null) {
        continue;
      }

      StatusResponse statusResponse = statusList.get(vehicle.getNumberPlate());
      if (statusResponse == null) {
        StatusResponse newStatusResponse = new StatusResponse();
        newStatusResponse.setColor(vehicle.getColor());
        newStatusResponse.setPlateNumber(vehicle.getNumberPlate());
        newStatusResponse.setSlotList(new ArrayList<>(List.of(i)));
        statusList.put(vehicle.getNumberPlate(), newStatusResponse);
      } else {
        List<Integer> slotList = statusResponse.getSlotList();
        slotList.add(i);
        statusResponse.setSlotList(slotList);
        statusList.put(vehicle.getNumberPlate(), statusResponse);
      }

    }

    for (Map.Entry<String, StatusResponse> entry : statusList.entrySet()) {
      resultBuilder.append(entry.getValue().getPlateNumber()).append(" ").append(entry.getValue().getColor()).append(" ")
          .append(entry.getValue().getSlotList()).append("\n");
    }
    return resultBuilder.toString();
  }

  public Boolean leave(Integer slotId) {

    if (garage.getGarageMap().get(slotId) == null) {
      log.warn("There is no car in  that slot");
      return Boolean.FALSE;
    }
    String planeNumber = garage.getGarageMap().get(slotId).getNumberPlate();
    boolean b = garage.getGarageMap().entrySet()
        .removeIf(entry -> entry.getValue().getNumberPlate().equals(planeNumber));
    if (Boolean.TRUE.equals(b)) {
      log.warn("Car leave plate number : " + planeNumber);
      return Boolean.TRUE;
    }

    return Boolean.TRUE;
  }

  public boolean validateVehicle(Vehicle vehicle) {

    if (!VehicleTypeEnum.isValidVehicleType(vehicle.getVehicleType())) {
      log.error("Vehicle Type didn't identify");
      return false;
    }
    if (!VehicleColorEnum.isValidVehicleColor(vehicle.getColor())) {
      log.error("Vehicle Color didn't identify");
      return false;
    }
    if (!isMatchPlatePattern(vehicle.getNumberPlate())) {
      log.error("Vehicle Plate Number didn't identify");
      return false;
    }

    return true;
  }

  public void fillSlot(Map<Integer, Vehicle> garage, Vehicle vehicle, int width, int emptySlotIndex) {
    for (int m = emptySlotIndex; m < (emptySlotIndex + width); m++) {
      garage.put(m, vehicle);
    }
  }

  public int getEmptySlotIndex(int width) {

    int count = 0;

    if (garage.getGarageMap().size() == 0) {
      return 1;
    }
    for (int j = 1; j <= 10; j++) {
      if (garage.getGarageMap().get(1) == null && count == width + 1) {
        return j - count;
      }

      if ((count == (width + 2)) || (j == 10 && count >= width && garage.getGarageMap().get(j) == null)) {
        return j - count + 1;
      }

      if (garage.getGarageMap().get(j) == null) {
        count++;
      } else {
        count = 0;
        continue;
      }
    }
    return -1;
  }


  public boolean isMatchPlatePattern(String numberPlate) {

    String[] split = numberPlate.split("-");
    if (split.length != 3) {
      return false;
    }
    int firstIndex;
    try {
      firstIndex = Integer.parseInt(split[0]);

    } catch (Exception e) {
      return false;
    }

    boolean isDoubleDigit = (firstIndex > 9 && firstIndex < 100);

    if (!isDoubleDigit) {
      return false;
    }

    // MiddleIndex
    if (!(isAlpha(split[1]) && split[1].length() <= 3 && split[1].length() > 0)) {
      return false;
    }

    int lastIndex;
    try {
      lastIndex = Integer.parseInt(split[2]);

    } catch (Exception e) {
      return false;
    }

    boolean isThreeOrLessDigit = (lastIndex > 99 && lastIndex <= 9999);

    if (!isThreeOrLessDigit) {
      return false;
    }
    return true;
  }

  public boolean isAlpha(String name) {
    char[] chars = name.toCharArray();

    for (char c : chars) {
      if (!Character.isLetter(c)) {
        return false;
      }
    }
    return true;
  }
}
