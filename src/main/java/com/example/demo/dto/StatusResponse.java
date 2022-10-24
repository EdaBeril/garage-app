package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class StatusResponse {

  private String plateNumber;
  private String color;
  private List<Integer> slotList;
}
