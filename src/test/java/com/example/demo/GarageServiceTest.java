package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.example.demo.dto.Garage;
import com.example.demo.dto.GarageRequest;
import com.example.demo.dto.Vehicle;
import com.example.demo.services.GarageServiceClientImpl;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class GarageServiceTest {


  @InjectMocks
  @Spy
  private GarageServiceClientImpl garageServiceClient;

  @Mock
  private Garage garage;


  @BeforeAll
  static void setup() {

    log.info("@BeforeAll - executes once before all test methods in this class");
  }

  @Test
  void WhenParkMethodOnlyHaveOneCarShouldBeTrue() {
    Vehicle vehicle = new Vehicle();
    vehicle.setVehicleType("Car");
    vehicle.setColor("BROWN");
    vehicle.setNumberPlate("34-FO-2044");
    GarageRequest garageRequest = new GarageRequest();
    garageRequest.setVehicle(vehicle);

    assertTrue( garageServiceClient.park(garageRequest) > 0);
  }

  @Test
  void WhenParkMethodHaveMultipleCarShouldBeTrue() {

    Map<Integer, Vehicle> newGarage = new HashMap<>();
    Vehicle newVehicle = new Vehicle();
    newVehicle.setVehicleType("Truck");
    newVehicle.setColor("Blue");
    newVehicle.setNumberPlate("34-TT-2012");

    newGarage.put(1, newVehicle);
    newGarage.put(2, newVehicle);
    newGarage.put(3, newVehicle);
    newGarage.put(4, newVehicle);

    Vehicle newVehicle2 = new Vehicle();
    newVehicle.setVehicleType("Jeep");
    newVehicle.setColor("Blue");
    newVehicle.setNumberPlate("34-TT-2012");

    newGarage.put(6, newVehicle2);
    newGarage.put(7, newVehicle2);

    garage.setGarageMap(newGarage);

    when(garage.getGarageMap()).thenReturn(newGarage);

    Vehicle vehicle = new Vehicle();
    vehicle.setVehicleType("Car");
    vehicle.setColor("BROWN");
    vehicle.setNumberPlate("34-FO-2044");
    GarageRequest garageRequest = new GarageRequest();
    garageRequest.setVehicle(vehicle);

    assertTrue( garageServiceClient.park(garageRequest) > 0);
  }

  @Test
  void WhenStatusMethodShouldBeTrue() {

    Map<Integer, Vehicle> newGarage = new HashMap<>();
    Vehicle newVehicle = new Vehicle();
    newVehicle.setVehicleType("Jeep");
    newVehicle.setColor("Blue");
    newVehicle.setNumberPlate("34-TT-2012");
    newGarage.put(1, newVehicle);
    garage.setGarageMap(newGarage);
    when(garage.getGarageMap()).thenReturn(newGarage);

    assertFalse(StringUtils.isBlank(garageServiceClient.status()));
  }

  @Test
  void WhenLeaveMethodShouldBeTrue() {

    Map<Integer, Vehicle> newGarage = new HashMap<>();
    Vehicle newVehicle = new Vehicle();
    newVehicle.setVehicleType("Car");
    newVehicle.setColor("Blue");
    newVehicle.setNumberPlate("34-TT-2012");
    newGarage.put(2, newVehicle);
    garage.setGarageMap(newGarage);
    when(garage.getGarageMap()).thenReturn(newGarage);

    assertTrue(garageServiceClient.leave(2));
  }

  @Test
  void WhenLeaveMethodShouldBeFalse() {

    Map<Integer, Vehicle> newGarage = new HashMap<>();
    Vehicle newVehicle = new Vehicle();
    newVehicle.setVehicleType("Jeep");
    newVehicle.setColor("Blue");
    newVehicle.setNumberPlate("34-TT-2012");
    newGarage.put(2, newVehicle);
    garage.setGarageMap(newGarage);
    when(garage.getGarageMap()).thenReturn(newGarage);

    assertFalse(garageServiceClient.leave(1));
  }
}
