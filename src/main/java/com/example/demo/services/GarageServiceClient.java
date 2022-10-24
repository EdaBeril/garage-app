package com.example.demo.services;

import com.example.demo.dto.GarageRequest;

public interface GarageServiceClient {

  Integer park(GarageRequest request);
  Boolean leave(Integer request);
  String status();
  int getEmptySlotIndex(int width);
}
