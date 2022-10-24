package com.example.demo.dto;

import lombok.Data;

import java.util.Map;

@Data
public class Garage {

  private Map<Integer, Vehicle> garageMap;

}
