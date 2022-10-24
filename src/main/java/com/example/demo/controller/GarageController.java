package com.example.demo.controller;

import com.example.demo.dto.GarageRequest;
import com.example.demo.services.GarageServiceClientImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/garage")
@RequiredArgsConstructor
public class GarageController {

  private final GarageServiceClientImpl garageService;

  @PostMapping("/park")
  public ResponseEntity<Integer> park(@RequestBody GarageRequest request) {
    return ResponseEntity.ok(garageService.park(request));
  }

  @GetMapping("/leave/{slotId}")
  public ResponseEntity<Boolean> leave(@PathVariable Integer slotId) {
    return ResponseEntity.ok(garageService.leave(slotId));
  }

  @GetMapping("/status")
  public ResponseEntity<String> status() {
    return ResponseEntity.ok(garageService.status());
  }
}
